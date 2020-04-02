package com.mukesh.notesappwithmvvm.database

import androidx.lifecycle.LiveData
import androidx.room.*

/*
 *This is a Room DAO (Data Access Object)
 *https://developer.android.com/training/data-storage/room/
 */
@Dao
interface NoteDao {
    @Insert
    fun insertData(note: Note?)

    @Update
    fun updateData(note: Note?)

    @Delete
    fun delete(note: Note?)

    @get:Query("SELECT * FROM note_table ORDER BY priority ASC")
    val allDataFromTable: LiveData<List<Note?>?>?

    @Query("SELECT * FROM note_table ORDER BY priority ASC")
    fun getAllTheData(): LiveData<List<Note?>?>?

    @Query("DELETE FROM note_table")
    fun deleteAllData()
}