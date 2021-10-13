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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.noteapp.R
import uz.gita.noteapp.entity.NoteData

class NotePageAdapter(val context: Context, var st :String, var data : List<NoteData>) : RecyclerView.Adapter <NotePageAdapter.NoteHolder>() {


    private var pos  : ((NoteData, Int) -> Unit)? = null
    inner class NoteHolder (view: View) : RecyclerView.ViewHolder(view)   {
        private val noteTitle : TextView = view.findViewById(R.id.textTitle)
        private val dateText : TextView = view.findViewById(R.id.dateText)
        private val pinned : ImageView = view.findViewById(R.id.btn_pin)

        init {
            itemView.setOnClickListener{
                pos?.invoke(data[adapterPosition], adapterPosition)
            }
        }
        fun bind(){
            val data2 = data[adapterPosition]
            val spanSt = SpannableString(data2.Title)
            val spanColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.purple_700))
            val startIndex = data2.Title.indexOf(st, 0, true)
            val lastIndex = startIndex + st.length
            if (startIndex ==-1) return
            spanSt.setSpan(spanColor,startIndex, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val d = data[adapterPosition]
            noteTitle.text = spanSt
           // noteTitle.text = d.Title
            dateText.text = d.time
           // if (d.isPinned) setBackgroundColor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note_page, parent, false))
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind()
    }

    fun setPosition (f: (NoteData, Int) -> Unit){
        pos = f
    }

    override fun getItemCount(): Int = data.size


}