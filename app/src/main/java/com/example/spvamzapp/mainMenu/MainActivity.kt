package com.example.spvamzapp.mainMenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spvamzapp.character.CharacterDatabase
import com.example.spvamzapp.character.CharacterRepository
import com.example.spvamzapp.character.ItemDatabase
import com.example.spvamzapp.character.ItemRepository
import com.example.spvamzapp.screens.CreateCharacterScreen
import com.example.spvamzapp.screens.EditCharacterScreen
import com.example.spvamzapp.screens.MainMenuScreen
import com.example.spvamzapp.spell.SpellDatabase
import com.example.spvamzapp.spell.SpellRepository
import com.example.spvamzapp.ui.theme.AppTheme
import com.example.spvamzapp.viewmodels.EditViewModel
import com.example.spvamzapp.viewmodels.EditViewModelFactory
import com.example.spvamzapp.viewmodels.MainMenuViewModel
import com.example.spvamzapp.viewmodels.MainMenuViewModelFactory

enum class ScreenNames() {
    MainMenuScreen,
    CreateCharacterScreen,
    EditCharacterScreen
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userPrefRepo = (this.application as MainApplication).userPrefRepo
        val repository =
            CharacterRepository(CharacterDatabase.getDatabase(applicationContext).characterDao())
        val viewModel: MainMenuViewModel by viewModels { MainMenuViewModelFactory(repository,
            userPrefRepo) }
        val itemRepo =
            ItemRepository(ItemDatabase.getDatabase(applicationContext).itemDao())
        val spellRepo =
            SpellRepository(SpellDatabase.getDatabase(applicationContext).spellDao())
        val editViewModel: EditViewModel by viewModels { EditViewModelFactory(itemRepo, spellRepo) }
        setContent {
            val theme = userPrefRepo.chosenTheme.collectAsState(0)
            when (theme.value) {
                0 -> {}
                else ->
                AppTheme(theme.value) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenNames.MainMenuScreen.name
                    ) {
                        composable(ScreenNames.MainMenuScreen.name) {
                            MainMenuScreen(
                                viewModel, editViewModel,
                                { navController.navigate(ScreenNames.EditCharacterScreen.name) },
                                { navController.navigate(ScreenNames.CreateCharacterScreen.name) })
                            {
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
}