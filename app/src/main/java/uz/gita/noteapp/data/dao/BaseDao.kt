package uz.gita.noteapp.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao <T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(data: T): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertOnly(data: T) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: T): Long

    @Delete
    fun delete(data: T) : Int

    @Update
    fun update(data: T): Int

}