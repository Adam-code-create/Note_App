package uz.gita.noteapp.ui.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.noteapp.R
import uz.gita.noteapp.entity.NoteData

class TrashPageAdapter(val context: Context, var st :String, var data : List<NoteData>) : RecyclerView.Adapter<TrashPageAdapter.NoteHolder>() {



    private var deleteListener  : ((NoteData, Int) -> Unit)? = null
    private var restoreListener  : ((NoteData, Int) -> Unit)? = null
    inner class NoteHolder (view: View) : RecyclerView.ViewHolder(view)   {
        private val noteTitle : TextView = view.findViewById(R.id.textTitle)
        private val dateText : TextView = view.findViewById(R.id.dateText)
        private val restore : TextView = view.findViewById(R.id.restore)
        private val delete : ImageView = view.findViewById(R.id.btn_delete)

        init {

        }
        fun bind(){
            val data2 = data[adapterPosition]
            val spanSt = SpannableString(data2.Title)
            val spanColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.purple_700))
            val startIndex = data2.Title.indexOf(st, 0, true)
            val lastIndex = startIndex + st.length
            spanSt.setSpan(spanColor,startIndex, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            noteTitle.text = spanSt
            noteTitle.text = data2.Title
            dateText.text = data2.time

            delete.setOnClickListener {
                deleteListener?.invoke(data[adapterPosition], adapterPosition)

            }
            restore.setOnClickListener {
                restoreListener?.invoke(data[adapterPosition], adapterPosition)

            }

           // if (d.isPinned) setBackgroundColor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trash_page, parent, false))
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind()
    }


    override fun getItemCount(): Int = data.size

    fun setDeleteListener (f: (NoteData, Int) -> Unit){
        deleteListener = f
    }
    fun setRestoreListener (f: (NoteData, Int) -> Unit){
       restoreListener = f
    }


}