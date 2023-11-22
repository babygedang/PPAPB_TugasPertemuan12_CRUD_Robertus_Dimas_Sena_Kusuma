package com.example.ppapb_tugaspertemuan12_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ppapb_pertemuan12_note.databinding.ActivityUpdateCatatanBinding


class UpdateCatatanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCatatanBinding
    private lateinit var db: CatatanDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = CatatanDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updatetitleEditText.setText(note.title)
        binding.updatecontentEditText.setText(note.content)

        binding.buttonSubmit.setOnClickListener{
            val newTitle = binding.updatetitleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val updatedNote = Catatan(noteId, newTitle, newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        }
    }
}