package com.mukesh.notesappwithmvvm.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/*
 *Room doesn't allow database operation in main thread
 */
@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    //ROOM auto generates all the necessary code we need here
    abstract fun noteDao(): NoteDao

    /*  private inner class PopulateDBAsyncTask internal constructor(db: NoteDatabase?) :
          AsyncTask<Void?, Void?, Void?>() {
          var noteDao: NoteDao
          protected override fun doInBackground(vararg voids: Void): Void? {
              noteDao.insertData(
                  Note(
                      "Title1",
                      "Description1",
                      1
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title2",
                      "Description2",
                      2
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title3",
                      "Description3",
                      3
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title4",
                      "Description4",
                      4
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title5",
                      "Description4",
                      5
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title6",
                      "Description4",
                      6
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title7",
                      "Description4",
                      7
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title8",
                      "Description4",
                      8
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title9",
                      "Description4",
                      9
                  )
              )
              noteDao.insertData(
                  Note(
                      "Title10",
                      "Description4",
                      10
                  )
              )
              return null
          }

          init {
              noteDao = db!!.noteDao()
          }

          override fun doInBackground(vararg p0: Void?): Void? {


          }
      }*/

    companion object {
        private var instance: NoteDatabase? = null
        @Synchronized
        fun getNoteDatabase(context: Context): NoteDatabase? {
            if (instance == null) instance =
                Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .addCallback(databaseCallBack)
                    .fallbackToDestructiveMigration().build()
            return instance
        }

        private val databaseCallBack: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //inserting some predefinedData
                 PopulateDBAsyncTask(instance).execute()
            }
        }
        class PopulateDBAsyncTask internal constructor(db: NoteDatabase?) :
            AsyncTask<Void?, Void?, Void?>() {

            var noteDao: NoteDao

            init {
                this.noteDao = db!!.noteDao()
            }

            override fun doInBackground(vararg p0: Void?): Void? {

                noteDao.insertData(
                    Note(
                        "Title1",
                        "Description1",
                        1
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title2",
                        "Description2",
                        2
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title3",
                        "Description3",
                        3
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title4",
                        "Description4",
                        4
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title5",
                        "Description4",
                        5
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title6",
                        "Description4",
                        6
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title7",
                        "Description4",
                        7
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title8",
                        "Description4",
                        8
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title9",
                        "Description4",
                        9
                    )
                )
                noteDao.insertData(
                    Note(
                        "Title10",
                        "Description4",
                        10
                    )
                )
                return null
            }
        }
    }
}