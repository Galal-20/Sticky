package com.galal.sticky.feature_note.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.galal.sticky.feature_note.presentation.notes.componenets.NoteItem
import com.galal.sticky.feature_note.presentation.notes.componenets.OrderSection
import com.galal.sticky.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
){
    var state = viewModel.state.value
    //val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
      floatingActionButton = {
         FloatingActionButton(onClick = {
             navController.navigate(Screen.AddEditNoteScreen.route)

         },Modifier.background(MaterialTheme.colorScheme.primary)
         )
         {
             Icon(imageVector = Icons.Default.Add,contentDescription = "Add note")
         }
      },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Your note",
                    style = MaterialTheme.typography.titleLarge)
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(imageVector = Icons.Default.List,contentDescription = "Sort")
                }
            }
            AnimatedVisibility(visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit =  fadeOut() + slideOutVertically() ,
            ) {
                OrderSection (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ){
                items(state.notes){note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                            navController.navigate(Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}")
                        },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if (result.equals("Undo")) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }

                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    }
}