package uz.gita.noteapp.ui.screens


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.noteapp.R
import uz.gita.noteapp.databinding.ScreenMainBinding
import uz.gita.noteapp.entity.NoteData
import uz.gita.noteapp.ui.adapter.NotePageAdapter
import uz.gita.noteapp.ui.modelviews.MainViewModel

class MainScreen : Fragment(R.layout.screen_main){
    private var _viewBinding : ScreenMainBinding? = null
    private val vb get() =  _viewBinding!!
    private val viewModel : MainViewModel by viewModels()
    private lateinit var adapter: NotePageAdapter
    private var querySt = ""
    private lateinit var data: List<NoteData>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewBinding = ScreenMainBinding.bind(view)

        viewModel.loadData()
        data = viewModel.loadData()
        adapter = NotePageAdapter(requireContext(),querySt, data)
        vb.list.adapter = adapter
        vb.list.layoutManager =LinearLayoutManager(requireContext())
        adapter.setPosition {data, pos ->
            val bundle = Bundle()
            bundle.putSerializable("data", data)
            findNavController().navigate(R.id.action_mainScreen_to_editNoteScreen, bundle)
        }
        if (data.isEmpty()){
            vb.noNotes.visibility = View.VISIBLE
            vb.noNotesDes.visibility = View.VISIBLE
        }else{
            vb.noNotes.visibility = View.GONE
            vb.noNotesDes.visibility = View.GONE
        }

        vb.delete.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_trashScreen)
        }

        val handler =Handler()
        vb.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                query?.let {
                    querySt = it.trim()
                    adapter.data = viewModel.searchLoadData("%${querySt}%")
                    searchResult(adapter.data.size)
                    adapter.st = querySt
                    adapter.notifyDataSetChanged()
                    vb.searchView.setQuery(querySt,false)
                }

                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                newText?.let {
                    querySt = it.trim()
                    adapter.data = viewModel.searchLoadData("%${querySt}%")
                    searchResult(adapter.data.size)
                    adapter.st = querySt
                    adapter.notifyDataSetChanged()
                }},500)
                return true
            }
        })
        vb.searchView.setQuery(null, false)
        vb.searchView.clearFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        vb.addBtn.setOnClickListener {

            findNavController().navigate(R.id.action_mainScreen_to_addNoteScreen)
        }
        viewModel.noteLivedata.observe(viewLifecycleOwner,notePageObserver)
    }


    private val notePageObserver = Observer<List<NoteData>>{
        for (i in it) adapter.notifyDataSetChanged()
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
        Log.d("TTT","pause")
    }

}
