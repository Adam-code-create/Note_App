package uz.gita.noteapp.ui.modelviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp.data.NoteDatabase
import uz.gita.noteapp.entity.NoteData
import uz.gita.noteapp.ui.adapter.NotePageAdapter

class TrashViewModel : ViewModel() {

    private val database = NoteDatabase.getDatabase().noteDeo()
    private val data = ArrayList<NoteData>()


    private val _trashLivedata = MutableLiveData<List<NoteData>>()
    val trashLivedata : LiveData<List<NoteData>> get() = _trashLivedata

    private val _searchLivedata = MutableLiveData<ArrayList<NoteData>>()
    val searchLivedata : LiveData<ArrayList<NoteData>> get() = _searchLivedata

    fun loadData() :List<NoteData >{
        _trashLivedata.value = database.getAllTrashNotes()
        return database.getAllTrashNotes()
    }

    fun searchLoadData(st : String) : List<NoteData> {
        _trashLivedata.value = database.getQueryText(st)
        return database.getQueryText(st)
    }

    fun delete (d :NoteData){
        database.delete(d)
        data.remove(d)
        _trashLivedata.value = database.getAllTrashNotes()
    }

    fun update (d :NoteData){
        database.update(d)
        data.remove(d)
       // _trashLivedata.value = database.getAllTrashNotes()
    }


}