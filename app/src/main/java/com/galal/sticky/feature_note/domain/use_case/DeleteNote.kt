package com.galal.sticky.feature_note.domain.use_case

import com.galal.sticky.feature_note.domain.model.Note
import com.galal.sticky.feature_note.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {
    suspend operator fun  invoke(note: Note){
        repository.deleteNote(note)
    }
}