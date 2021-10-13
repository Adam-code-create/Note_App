package uz.gita.noteapp.ui.modelviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp.data.NoteDatabase
import uz.gita.noteapp.entity.NoteData
import uz.gita.noteapp.ui.adapter.NotePageAdapter

class MainViewModel : ViewModel() {

    private val database = NoteDatabase.getDatabase().noteDeo()


    private val _noteLivedata = MutableLiveData<List<NoteData>>()
    val noteLivedata : LiveData<List<NoteData>> get() = _noteLivedata

    private val _searchLivedata = MutableLiveData<ArrayList<NoteData>>()
    val searchLivedata : LiveData<ArrayList<NoteData>> get() = _searchLivedata

    fun loadData() : List<NoteData >{
        _noteLivedata.value = database.getAllNotes()
        return database.getAllNotes()
    }

    fun searchLoadData(st : String) : List<NoteData> {
        _noteLivedata.value = database.getQueryText(st)
        return database.getQueryText(st)
    }


}