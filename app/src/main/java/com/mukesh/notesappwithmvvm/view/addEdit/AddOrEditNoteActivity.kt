package com.mukesh.notesappwithmvvm.view.addEdit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mukesh.notesappwithmvvm.R
import com.mukesh.notesappwithmvvm.database.Note
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_note.*

class AddOrEditNoteActivity : AppCompatActivity() {

    private var note: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        //in manifest need to declare android:parentActivityName=".view.MainActivity" to get X button visible
        if (supportActionBar != null) supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        initView()
    }

    private fun initView() {
        priorityNumber.minValue = 1
        priorityNumber.maxValue = 20

        note = intent?.getSerializableExtra("bundle") as? Note

        note?.let {
            title = "Edit Note"
            editTitle.setText(note!!.title)
            editDescription.setText(note!!.description)
            priorityNumber.value = note!!.priority
            return
        }
        title = "Add Note"
        note = Note()
        /*   if (note != null) {
               title = "Edit Note"
               editTitle.setText(note!!.title)
               editDescription.setText(note!!.description)
               priorityNumber.setValue(note!!.priority)
           } else {
               title = "Add Note"
               note = Note()
           }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.note_menu, menu)
        //Return false if you don't want to show the menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Respond to the action bar's Up/Home button
        if (item.itemId == android.R.id.home) {
            supportFinishAfterTransition()
            return true
        } else if (item.itemId == R.id.saveNote) {
            saveNote()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {

        if (editTitle!!.text == null || editDescription!!.text == null) return
        val title = editTitle!!.text.toString().trim { it <= ' ' }
        val description = editDescription!!.text.toString().trim { it <= ' ' }
        val priorityNo = priorityNumber!!.value
        if (title.trim { it <= ' ' }.isEmpty() || description.isEmpty()) {
            Toasty.error(this, "Please enter title and description", Toast.LENGTH_SHORT, true)
                .show()
            return
        }
        note!!.title = title
        note!!.priority = priorityNo
        note!!.description = description
        val intent = Intent()
        intent.putExtra("bundle", note)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}