package com.galal.sticky.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.galal.sticky.feature_note.presentation.add_edit_note.AddEditeNoteScreen
import com.galal.sticky.feature_note.presentation.notes.NoteScreen
import com.galal.sticky.feature_note.presentation.util.Screen
import com.galal.sticky.ui.theme.StickyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { StickyTheme {
            Surface(
                color = MaterialTheme.colorScheme.background

            ) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.NoteScreen.route ){
                    composable(route = Screen.NoteScreen.route){
                        NoteScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(name = "noteId"){
                            type = androidx.navigation.NavType.IntType
                            defaultValue = -1
                        },
                            navArgument(name = "noteColor") {
                                type = androidx.navigation.NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ){
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditeNoteScreen(navController = navController, noteColor =color)
                    }
                }
            }
        }
        }
        }
}
