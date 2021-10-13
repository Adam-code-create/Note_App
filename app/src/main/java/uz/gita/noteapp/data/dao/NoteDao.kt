package uz.gita.noteapp.data.dao


import androidx.room.Dao
import androidx.room.Query
import uz.gita.noteapp.entity.NoteData

@Dao
interface NoteDao : BaseDao <NoteData>{
    @Query("SELECT * FROM NoteData WHERE isTrash = 0")
    fun getAllNotes (): List<NoteData>

    @Query("SELECT * FROM NoteData WHERE isTrash = 1")
    fun getAllTrashNotes (): List<NoteData>

    @Query("SELECT * FROM NoteData WHERE NoteData.isTrash = 0 AND NoteData.Title LIKE :query")
    fun getQueryText(query : String): List<NoteData>

    @Query("SELECT * FROM NoteData WHERE NoteData.isTrash = 1 AND NoteData.Title LIKE :query")
    fun getTrashQueryText(query : String): List<NoteData>



}