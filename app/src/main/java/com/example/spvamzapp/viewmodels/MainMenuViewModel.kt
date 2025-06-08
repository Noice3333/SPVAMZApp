package com.example.spvamzapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.character.CharacterRepository
import com.example.spvamzapp.mainMenu.UserPrefRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//ViewModel, ktorý poskytuje možnosť hlavnému menu prispôsobovať sa na zmeny preferencii
// používateľa,
// a určitým obrazovkám upravovať, vytvárať alebo odstraňovať postavy
class MainMenuViewModel(private val repository: CharacterRepository,
    private val userPrefRepo: UserPrefRepo) : ViewModel() {

    val mainMenuUiState: StateFlow<MainScreenUiState> =
        repository.allCharacters.map {
            MainScreenUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MainScreenUiState()
        )

    fun changeTheme(choice: Int) {
        viewModelScope.launch {
            userPrefRepo.saveThemePreference(choice)
        }
    }

    fun addCharacter(characterEntry: CharacterEntry) {
        viewModelScope.launch {
            repository.insert(characterEntry)
        }
    }

    fun editCharacter(characterEntry: CharacterEntry) {
        viewModelScope.launch {
            repository.update(characterEntry)
        }
    }

    fun removeCharacter(characterEntry: CharacterEntry) {
        viewModelScope.launch {
            repository.delete(characterEntry)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MainMenuViewModelFactory(private val characterRepository: CharacterRepository,
    private val userPrefRepo: UserPrefRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainMenuViewModel(characterRepository, userPrefRepo) as T
    }
}

data class MainScreenUiState (val charList: List<CharacterEntry> = listOf())