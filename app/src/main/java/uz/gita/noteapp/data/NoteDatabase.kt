package uz.gita.noteapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.noteapp.data.dao.NoteDao
import uz.gita.noteapp.entity.NoteData
import java.lang.NullPointerException

@Database(
    entities = [NoteData::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase (){
    abstract fun noteDeo () : NoteDao
    companion object {
        private var database : NoteDatabase?=null
        fun init (context: Context){
            if (database != null) return
            database = Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDatabase")
                .allowMainThreadQueries()
                .build()
        }

        fun getDatabase() = database ?: throw NullPointerException("You must call init first")
    }


}