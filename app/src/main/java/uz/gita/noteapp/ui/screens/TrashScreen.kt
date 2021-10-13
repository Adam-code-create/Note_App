package uz.gita.noteapp.ui.screens


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.noteapp.R
import uz.gita.noteapp.databinding.ScreenDeleteBinding
import uz.gita.noteapp.databinding.ScreenMainBinding
import uz.gita.noteapp.entity.NoteData
import uz.gita.noteapp.ui.adapter.NotePageAdapter
import uz.gita.noteapp.ui.adapter.TrashPageAdapter
import uz.gita.noteapp.ui.modelviews.MainViewModel
import uz.gita.noteapp.ui.modelviews.TrashViewModel
import java.text.SimpleDateFormat

class TrashScreen : Fragment(R.layout.screen_delete){
    private var _viewBinding : ScreenDeleteBinding? = null
    private val vb get() =  _viewBinding!!
    private val viewModel : TrashViewModel by viewModels()
    private lateinit var adapter: TrashPageAdapter
    private var querySt = ""
    private lateinit var data: ArrayList<NoteData>


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewBinding = ScreenDeleteBinding.bind(view)
        data = viewModel.loadData() as ArrayList<NoteData>
        adapter = TrashPageAdapter(requireContext(),querySt, data)
        vb.listBin.adapter = adapter
        vb.listBin.layoutManager =LinearLayoutManager(requireContext())
        if (data.isEmpty()) vb.noNotesBin.visibility = View.VISIBLE
        else vb.noNotesBin.visibility = View.GONE

        adapter.setRestoreListener {it, pos->
            viewModel.update(NoteData(
                it.id, it.Title, it.description,it.time, 0, it.isPinned
            ))
            data.remove(it)
            adapter.notifyItemRemoved(pos)
            if (data.isEmpty()) vb.noNotesBin.visibility = View.VISIBLE
            else vb.noNotesBin.visibility = View.GONE

        }
        adapter.setDeleteListener {it, pos->
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Do you want to delete a note? ")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialogInterface, i ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        viewModel.delete(it)
                        data.remove(it)
                        adapter.notifyItemRemoved(pos)
                        if (data.isEmpty()) vb.noNotesBin.visibility = View.VISIBLE
                        else vb.noNotesBin.visibility = View.GONE
                    }
                }
                .setNegativeButton("No") { dialogInterface, i ->
                }
                .show()
        }
        vb.back.setOnClickListener {
            findNavController().popBackStack()
        }
        vb.searchBin.setOnClickListener {
            vb.searchLayoutBin.visibility = View.VISIBLE
        }
        val handler = Handler(Looper.getMainLooper())
        vb.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                query?.let {
                    querySt = it.trim()
                    adapter.data = viewModel.searchLoadData("%${querySt}%")
                    searchResult(adapter.data.size)
                    adapter.st = querySt
                    adapter.notifyDataSetChanged()

                }
                searchResult(adapter.data.size)
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                newText?.let {
                    querySt = it.trim()
                    adapter.data = viewModel.searchLoadData("%${querySt}%")
                    adapter.st = querySt
                    searchResult(adapter.data.size)
                    adapter.notifyDataSetChanged()
                    vb.searchView.setQuery(querySt,false)
                }},500)
                return true
            }
        })
        viewModel.trashLivedata.observe(viewLifecycleOwner,notePageObserver)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val notePageObserver = Observer<List<NoteData>>{
        for (i in it)
        adapter.notifyDataSetChanged()
    }

    private fun searchResult(count :Int){
        if (count == 0 && data.isNotEmpty()){
            vb.searchResult.visibility =View.VISIBLE
            vb.searchResultText.visibility = View.VISIBLE
        } else {
            vb.searchResult.visibility =View.GONE
            vb.searchResultText.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding =null
    }

}
