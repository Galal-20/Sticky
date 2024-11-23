package com.galal.sticky.feature_note.presentation.notes

import com.galal.sticky.feature_note.domain.util.NoteOrder
import com.galal.sticky.feature_note.domain.util.OrderType
import com.galal.sticky.feature_note.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)