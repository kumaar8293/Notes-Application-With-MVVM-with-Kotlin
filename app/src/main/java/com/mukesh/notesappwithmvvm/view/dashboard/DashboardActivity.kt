package com.mukesh.notesappwithmvvm.view.dashboard

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mukesh.notesappwithmvvm.R
import com.mukesh.notesappwithmvvm.database.Note
import com.mukesh.notesappwithmvvm.view.addEdit.AddOrEditNoteActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(),
    DashboardAdapterWithAnimation.CustomOnItemClickListener {
    val ADD_REQUEST_CODE = 1
    val EDIT_REQUEST_CODE = 2
    //    private DashboardAdapter adapter;
    private var adapter: DashboardAdapterWithAnimation? = null
    private var viewModel: DashboardViewModel? = null
    private var no_notes: LottieAnimationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        viewModel!!.getAllNoteList(this)!!.observe(this,
            Observer {

                //Custom Datapass
                // adapter.setData(notes);
                //Adapter with animation
                adapter!!.submitList(it)

                if (it != null && it.isEmpty()) {
                    no_notes!!.visibility = View.VISIBLE
                } else {
                    no_notes!!.visibility = View.GONE
                }
            })
    }

    private fun initUI() {
        val appBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        appBar.setOnMenuItemClickListener { item ->
            // Handle actions based on the menu item
            if (item.itemId == R.id.menuSettings) {
                Toasty.info(this@DashboardActivity, "Do your work", Toast.LENGTH_SHORT, true)
                    .show()
            } else if (item.itemId == R.id.menuDelete) {
                showDialog()
            }
            true
        }
        appBar.setNavigationOnClickListener {
            // Handle the navigation click by showing a BottomDrawer etc.
            Toasty.info(this@DashboardActivity, "Do your work", Toast.LENGTH_SHORT, true).show()
        }
        adapter = DashboardAdapterWithAnimation(this, this)
        recycler_view.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                viewModel!!.deleteNote(adapter!!.getNoteAtPosition(viewHolder.adapterPosition))
                Toasty.success(this@DashboardActivity, "Item deleted", Toast.LENGTH_SHORT, true)
                    .show()
            }
        }).attachToRecyclerView(recycler_view)
        findViewById<View>(R.id.addNote).setOnClickListener {
            val intent = Intent(
                this@DashboardActivity,
                AddOrEditNoteActivity::class.java
            )
            startActivityForResult(intent, ADD_REQUEST_CODE)
        }
        no_notes = findViewById(R.id.no_notes)
    }

    override fun onItemClick(
        note: Note,
        itemView: View
    ) {
        val intent = Intent(this, AddOrEditNoteActivity::class.java)
        intent.putExtra("bundle", note)
        // Now we provide a list of Pair items which contain the view we can transitioning
// from, and the name of the view it is transitioning to, in the launched activity
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            Pair.create(
                itemView.findViewById(
                    R.id.itemTitle
                ), this.getString(R.string.title_transition)
            ),
            Pair.create(
                itemView.findViewById(
                    R.id.itemDescription
                ), this.getString(R.string.desc_transition)
            )
        )
        startActivityForResult(intent, EDIT_REQUEST_CODE, options.toBundle())
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete All Notes")
            .setMessage("Are you sure? You want to delete all the notes.")
            .setPositiveButton("Ok") { dialog, which ->
                viewModel!!.deleteAllNote()
                Toasty.success(
                    this@DashboardActivity,
                    "All notes deleted",
                    Toast.LENGTH_SHORT,
                    true
                ).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
            .show()
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val note =
                data.getSerializableExtra("bundle") as Note
            viewModel!!.insertNote(note)
            Toasty.success(this, "Note saved", Toast.LENGTH_SHORT, true).show()
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val note =
                data.getSerializableExtra("bundle") as Note
            viewModel!!.updateNote(note)
            Toasty.success(this, "Note updated", Toast.LENGTH_SHORT, true).show()
        } else {
            Toasty.error(this, "Note not saved", Toast.LENGTH_SHORT, true).show()
        }
    }
}