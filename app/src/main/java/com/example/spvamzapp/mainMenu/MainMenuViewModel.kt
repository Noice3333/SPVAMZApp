package com.example.spvamzapp.mainMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.character.CharacterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainMenuViewModel(private val repository: CharacterRepository) : ViewModel() {

    val mainMenuUiState: StateFlow<MainScreenUiState> =
        repository.allCharacters.map {
            MainScreenUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MainScreenUiState()
        )

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
class MainMenuViewModelFactory(private val characterRepository: CharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainMenuViewModel(characterRepository) as T
    }
}

data class MainScreenUiState (val charList: List<CharacterEntry> = listOf())