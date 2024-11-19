package com.galal.sticky.di

import android.app.Application
import androidx.room.Room
import com.galal.sticky.feature_note.data.data_source.NoteDataBase
import com.galal.sticky.feature_note.data.repository.NoteRepositoryImp
import com.galal.sticky.feature_note.domain.repository.NoteRepository
import com.galal.sticky.feature_note.domain.use_case.DeleteNote
import com.galal.sticky.feature_note.domain.use_case.GetNotes
import com.galal.sticky.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerNoteDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providerRepository(db: NoteDataBase): NoteRepository{
        return NoteRepositoryImp(db.noteDao)
    }

    @Provides
    @Singleton
    fun providerNoteUseCases(repository: NoteRepository): NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}