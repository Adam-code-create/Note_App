package uz.gita.noteapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NoteData (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val Title : String,
    val description : String,
    var time : String,
    var isTrash : Int, // 0 note, 1 trash
    var isPinned : Boolean
) : Serializable
