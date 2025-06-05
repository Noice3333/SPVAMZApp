package com.example.spvamzapp.mainMenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spvamzapp.character.CharacterDatabase
import com.example.spvamzapp.character.CharacterRepository
import com.example.spvamzapp.character.ItemDatabase
import com.example.spvamzapp.character.ItemRepository
import com.example.spvamzapp.ui.theme.AppTheme

enum class ScreenNames() {
    MainMenuScreen,
    CreateCharacterScreen,
    EditCharacterScreen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository =
            CharacterRepository(CharacterDatabase.getDatabase(applicationContext).characterDao())
        val viewModel: MainMenuViewModel by viewModels {MainMenuViewModelFactory(repository)}
        val itemRepo =
            ItemRepository(ItemDatabase.getDatabase(applicationContext).itemDao())
        val editViewModel: EditViewModel by viewModels { EditViewModelFactory(itemRepo) }
        setContent {
            var choiceOfTheme by rememberSaveable { mutableIntStateOf(2) }
            AppTheme(choice = choiceOfTheme) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenNames.MainMenuScreen.name
                ) {
                    composable(ScreenNames.MainMenuScreen.name) {
                        MainMenuScreen(viewModel, editViewModel, {
                            navController.navigate(ScreenNames.EditCharacterScreen.name)
                        }
                        ) {
                            navController.navigate(ScreenNames.CreateCharacterScreen.name)
                        }
                    }
                    composable(ScreenNames.CreateCharacterScreen.name) {
                        CreateCharacterScreen(viewModel) {
                            navController.navigate(ScreenNames.MainMenuScreen.name)
                        }
                    }
                    composable(ScreenNames.EditCharacterScreen.name) {
                        EditCharacterScreen(viewModel, editViewModel) {
                            navController.navigate(ScreenNames.MainMenuScreen.name)
                        }
                    }
                }
            }
        }
    }
}