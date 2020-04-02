package com.mukesh.notesappwithmvvm.view.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mukesh.notesappwithmvvm.database.Note
import com.mukesh.notesappwithmvvm.repositories.NoteRepositories

class DashboardViewModel : ViewModel() {
    private var noteRepositories: NoteRepositories? = null
    private var allNoteList: LiveData<List<Note?>?>? =
        null

    private fun initializeComponents(context: Context) {
        if (allNoteList == null) {
            noteRepositories = NoteRepositories(context)
            allNoteList = noteRepositories!!.allNotes
        }
    }

    fun insertNote(note: Note?) {
        noteRepositories!!.insertData(note)
    }

    fun updateNote(note: Note?) {
        noteRepositories!!.updateData(note)
    }

    fun deleteNote(note: Note?) {
        noteRepositories!!.deleteData(note)
    }

    fun deleteAllNote() {
        noteRepositories!!.deleteAllData()
    }

    fun getAllNoteList(context: Context): LiveData<List<Note?>?>? {
        if (allNoteList == null) {
            initializeComponents(context)
        }
        return allNoteList
    }
}