package com.example.spvamzapp.mainMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.character.ItemRepository
import com.example.spvamzapp.item.Item
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditViewModel(private val repository: ItemRepository) : ViewModel() {

    var selectedChar: CharacterEntry? = null
    val editUiState: StateFlow<EditUiState> =
        repository.allItems
            .map {
                EditUiState(it.filter { it.charId == selectedChar?.id })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = EditUiState()
        )

    fun addItem(item: Item) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun editItem(item: Item) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun removeItem(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class EditViewModelFactory(private val itemRepository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditViewModel(itemRepository) as T
    }
}

data class EditUiState (val itemList: List<Item> = listOf<Item>())