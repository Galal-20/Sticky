package com.galal.sticky.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val notesColors = listOf(Color.Red, Color.DarkGray, Color.LightGray, Color.Yellow, Color
            .Blue, Color.Magenta)
    }
}


class  InvalidNoteException(message: String): Exception(message)
