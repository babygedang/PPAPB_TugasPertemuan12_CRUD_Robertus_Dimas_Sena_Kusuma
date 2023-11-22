package com.example.ppapb_tugaspertemuan12_crud

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ppapb_pertemuan12_note.R

class CatatanAdapter(private var notes: List<Catatan>, context: Context) :
    RecyclerView.Adapter<CatatanAdapter.NoteViewHolder>() {

    private val db: CatatanDatabaseHelper = CatatanDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catatan_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateCatatanActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            // Menampilkan dialog konfirmasi sebelum menghapus
            showDeleteConfirmationDialog(holder.itemView.context, note)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, note: Catatan) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Konfirmasi Hapus")
        builder.setMessage("Apakah Anda yakin ingin menghapus catatan ini?")

        builder.setPositiveButton("Ya") { _, _ ->
            // Hapus catatan jika pengguna menekan tombol "Ya"
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(context, "Catatan dihapus", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Tidak") { _, _ ->
            // Tidak melakukan apa-apa jika pengguna memilih untuk tidak menghapus
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun refreshData(newNotes: List<Catatan>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}