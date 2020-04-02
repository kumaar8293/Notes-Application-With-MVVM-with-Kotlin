package com.mukesh.notesappwithmvvm.view.dashboard

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.notesappwithmvvm.R
import com.mukesh.notesappwithmvvm.database.Note
import java.util.*

class DashboardAdapter(context: Context, private val listener: CustomOnItemClickListener?) : RecyclerView.Adapter<DashboardAdapter.NoteHolder>() {
    private var notes: List<Note> =
        ArrayList()
    // create instance of Random class
    private val rand = Random()
    private lateinit var background: Array<Drawable?>
    private fun initBackground(context: Context) {
        background = arrayOf(
            context.getDrawable(R.drawable.dashboard_item1_background),
            context.getDrawable(R.drawable.dashboard_item2_background),
            context.getDrawable(R.drawable.dashboard_item3_background),
            context.getDrawable(R.drawable.dashboard_item4_background)
        )
    }

    init {
        initBackground(context)
    }
    fun getNoteAtPosition(position: Int): Note {
        return notes[position]
    }

    fun setData(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.dash_board_single_row, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: NoteHolder,
        position: Int
    ) {
        val currentNote = notes[position]
        holder.noteTitle.text = currentNote.title
        holder.noteDescription.text = currentNote.description
        holder.parentLayout.background = randomNumber
        holder.priorityNumber.text = "" + currentNote.priority
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var noteTitle: TextView
        var noteDescription: TextView
        var priorityNumber: TextView
        var parentLayout: ConstraintLayout

        init {
            noteTitle = itemView.findViewById(R.id.itemTitle)
            noteDescription = itemView.findViewById(R.id.itemDescription)
            parentLayout = itemView.findViewById(R.id.parentLayout)
            priorityNumber = itemView.findViewById(R.id.priorityNumber)
            itemView.setOnClickListener {
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(notes[adapterPosition], itemView)
                }
            }
        }
    }

    interface CustomOnItemClickListener {
        fun onItemClick(
            note: Note?,
            itemView: View?
        )
    }

    // Generate random integers in range 0 to length of array-1
    private val randomNumber: Drawable?
        private get() { // Generate random integers in range 0 to length of array-1
            val randomNumber = rand.nextInt(background.size)
            return background[randomNumber]
        }

}