package uz.gita.noteapp.ui.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.noteapp.R
import uz.gita.noteapp.databinding.ScreenAddNoteBinding
import uz.gita.noteapp.entity.NoteData
import uz.gita.noteapp.ui.modelviews.AddNoteViewModel
import java.text.SimpleDateFormat

class AddNoteScreen :Fragment(R.layout.screen_add_note),  AdapterView.OnItemSelectedListener {
    private var _viewBinding : ScreenAddNoteBinding? = null
    private val vb get() =  _viewBinding!!
    private val viewModel : AddNoteViewModel by viewModels()
    private var isPinned = false
    var list = ArrayList<ImageView>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewBinding = ScreenAddNoteBinding.bind(view)
        findColorView(view)
        ArrayAdapter.createFromResource(requireContext(), R.array.number_array,android.R.layout.simple_spinner_item).also {
           adapter->
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
           vb.spinnerAdd.adapter = adapter
       }
        vb.editTitle.isFocused
        vb.buttonTextColor.setOnClickListener {

               vb.colorLine1.visibility = View.VISIBLE
               vb.colorLine2.visibility = View.VISIBLE
            for (i in 0 until list.size) {
                list[i].setOnClickListener {
                    if (vb.editTitle.isFocused){
                        vb.editTitle.setTextColor(ContextCompat.getColor(requireContext(), selectColor(i)))
                        vb.colorLine1.visibility = View.GONE
                        vb.colorLine2.visibility = View.GONE
                    }else {
                        vb.editor.setEditorFontColor(ContextCompat.getColor(requireContext(),selectColor(i)))
                        vb.colorLine1.visibility = View.GONE
                        vb.colorLine2.visibility = View.GONE
                    }

                }
            }

        }
        vb.spinnerAdd.onItemSelectedListener = this
        vb.editor.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.addNoteBg))
        vb.editor.setPadding(10,10, 10,10)
        vb.editor.setPlaceholder("Insert note here")
        var bold = true
        vb.buttonTextStyleBold.setOnClickListener {
            bold = if (bold){
                vb.editor.setBold()
                vb.buttonTextStyleBold.setImageResource(R.drawable.ic_bold_format2)
                false

            } else{
                vb.editor.setBold()
                vb.buttonTextStyleBold.setImageResource(R.drawable.ic_bold_format)
                true
            }

        }
        var italic = true
        vb.buttonTextStyleItalic.setOnClickListener {
            italic = if (italic){
                vb.editor.setItalic()
                vb.buttonTextStyleItalic.setImageResource(R.drawable.ic_italic_format2)
                false
            }else{
                vb.editor.setItalic()
                vb.buttonTextStyleItalic.setImageResource(R.drawable.ic_italic_format)
                true
            }

        }
        var underline = true
        vb.buttonTextStyleunderline.setOnClickListener {
            underline = if (underline){
                vb.editor.setUnderline()
                vb.buttonTextStyleunderline.setImageResource(R.drawable.ic_underline_format2)
                false
            }else{
                vb.editor.setUnderline()
                vb.buttonTextStyleunderline.setImageResource(R.drawable.ic_underline_format)
                true
            }
        }
        vb.buttonSave.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                viewModel.insert(NoteData(
                    0,
                    vb.editTitle.text.toString(),
                    vb.editor.html.toString(),
                    SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time),
                    0,
                    isPinned
                ))
            }
            findNavController().popBackStack()
        }

        vb.buttonMore.setOnClickListener {it->
            val popUp = PopupMenu (requireContext(), it)
            popUp.setOnMenuItemClickListener {
                if (it.itemId == R.id.delete){
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()
                } else{
                    if (vb.editor.html.isNullOrBlank()){
                        Toast.makeText(requireContext(), "Editor is empty", Toast.LENGTH_SHORT).show()
                    }else{
                        val text = vb.editor.html.toString()
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, text)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }

                }
                return@setOnMenuItemClickListener true
            }
            popUp.inflate(R.menu.menu_pop_up)
            popUp.show()
        }
        var notEmpty = true
        vb.buttonBack.setOnClickListener {
            if (vb.editor.html.isNullOrBlank()){
                notEmpty = false
            }
            if (notEmpty){
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Do you want to save a note? ")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialogInterface, i ->

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            viewModel.insert(NoteData(
                                0,
                                vb.editTitle.text.toString(),
                                vb.editor.html.toString(),
                                SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time),
                                0,
                                isPinned
                            ))
                        }
                        findNavController().popBackStack()
                    }
                    .setNegativeButton("No") { dialogInterface, i ->
                        findNavController().popBackStack()

                    }
                    .show()

            } else{
                findNavController().popBackStack()
            }

        }
    }


    var check = true
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        if (check){
            parent?.setSelection(3)
            check = false
        } else{
            val item = parent?.getItemAtPosition(pos).toString()
            when(pos){
                0->{
                    vb.textSize.text = item
                    vb.editor.setFontSize(7)
                }
                1->{
                    vb.textSize.text = item
                    vb.editor.setFontSize(6)
                }
                2->{
                    vb.textSize.text = item
                    vb.editor.setFontSize(5)
                }
                3->{
                    vb.textSize.text = item
                    vb.editor.setFontSize(4)
                }
                4->{
                    vb.textSize.text = item
                    vb.editor.setFontSize(3)
                }
                5->{
                    vb.textSize.text = item
                    vb.editor.setFontSize(2)
                }
            }

        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun selectColor(pos : Int): Int{
        return when(pos){
            1->R.color.color_1
            2->R.color.color_2
            3->R.color.color_3
            4->R.color.color_4
            5->R.color.color_5
            6->R.color.color_6
            7->R.color.color_7
            8->R.color.color_8
            9->R.color.color_9
            10->R.color.color_10
            11->R.color.color_11
            12->R.color.color_12
            13->R.color.color_13
            14->R.color.color_14
            else ->R.color.color_15
        }
    }

    private fun findColorView(view :View) {
        val linear1 = view.findViewById<LinearLayout>(R.id.colorLine1)
        val linear2 = view.findViewById<LinearLayout>(R.id.colorLine2)
        for (i in 0 until linear1.childCount) {
            list.add(linear1.getChildAt(i) as ImageView)
        }
        for (i in 0 until linear2.childCount) {
            list.add(linear2.getChildAt(i) as ImageView)
        }
    }


       override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}