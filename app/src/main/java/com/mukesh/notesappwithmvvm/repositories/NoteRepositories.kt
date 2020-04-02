package com.mukesh.notesappwithmvvm.repositories

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.mukesh.notesappwithmvvm.database.Note
import com.mukesh.notesappwithmvvm.database.NoteDao
import com.mukesh.notesappwithmvvm.database.NoteDatabase.Companion.getNoteDatabase

/*
 * Room doesn't allow database operation in main thread
 */
class NoteRepositories(application: Context?) {
    private val noteDao: NoteDao
    val allNotes: LiveData<List<Note?>?>?
    fun insertData(note: Note?) { //new InsertNoteTask(noteDao).execute(note);
        DatabaseTask(noteDao, 1).execute(note)
    }

    fun updateData(note: Note?) { //new UpdateNoteTask(noteDao).execute(note);
        DatabaseTask(noteDao, 2).execute(note)
    }

    fun deleteData(note: Note?) { // new DeleteNoteTask(noteDao).execute(note);
        DatabaseTask(noteDao, 3).execute(note)
    }

    fun deleteAllData() { // new DeleteAllNotesTask(noteDao).execute();
        DatabaseTask(noteDao, 4).execute(null as Note?)
    }

    /*  private class DatabaseTask internal constructor(var noteDao: NoteDao, var type: Int) :
          AsyncTask<Note?, Void?, Void?>() {
          protected override fun doInBackground(vararg notes: Note): Void? {
              when (type) {
                  1 -> noteDao.insertData(notes[0])
                  2 -> noteDao.updateData(notes[0])
                  3 -> noteDao.delete(notes[0])
                  4 -> noteDao.deleteAllData()
              }
              return null
          }

          override fun doInBackground(vararg p0: Note?): Void? {

          }

      }
  */
    class DatabaseTask internal constructor(var noteDao: NoteDao, var type: Int) :
        AsyncTask<Note?, Void?, Void?>() {
        override fun doInBackground(vararg notes: Note?): Void? {
            when (type) {
                1 -> noteDao.insertData(notes[0])
                2 -> noteDao.updateData(notes[0])
                3 -> noteDao.delete(notes[0])
                4 -> noteDao.deleteAllData()
            }
            return null
        }


    }

    /*private static class InsertNoteTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        InsertNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insertData(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        UpdateNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.updateData(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        DeleteNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesTask extends AsyncTask<Void, Void, Void> {
        NoteDao noteDao;

        DeleteAllNotesTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {

            noteDao.deleteAllData();
            return null;
        }
    }
*/

    init {
        val noteDatabase = getNoteDatabase(application!!)
        //We can call the below method because room generate all the necessary codes
        noteDao = noteDatabase!!.noteDao()
        allNotes = noteDao.allDataFromTable
    }
}