package com.galal.sticky.feature_note.data.repository

import com.galal.sticky.feature_note.data.data_source.NoteDao
import com.galal.sticky.feature_note.domain.model.Note
import com.galal.sticky.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImp(private val noteDao: NoteDao): NoteRepository {
    override fun getNote(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}