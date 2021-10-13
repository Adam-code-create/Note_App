package uz.gita.noteapp.ui.modelviews

import androidx.lifecycle.ViewModel
import uz.gita.noteapp.data.NoteDatabase
import uz.gita.noteapp.entity.NoteData

class AddNoteViewModel :ViewModel() {

    private val database = NoteDatabase.getDatabase().noteDeo()
    private val data = ArrayList<NoteData>()


    fun insert (d : NoteData){
        database.insert(d)
        data.add(d)
    }

    fun delete (d :NoteData){
        database.delete(d)
        data.remove(d)
    }

    fun update (d :NoteData){
        database.update(d)

    }

}