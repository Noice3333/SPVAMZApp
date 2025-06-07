package com.example.spvamzapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.character.ItemRepository
import com.example.spvamzapp.item.Item
import com.example.spvamzapp.spell.Spell
import com.example.spvamzapp.spell.SpellRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditViewModel(
    private val itemRepository: ItemRepository,
    private val spellRepository: SpellRepository

) : ViewModel() {

    var selectedChar: CharacterEntry? = null
    var itemFlowList: Flow<List<Item>> =
        itemRepository.allItems
            .map {
                (it.filter { it.charId == selectedChar?.id })
        }
    var spellFlowList: Flow<List<Spell>> =
        spellRepository.allSpells
            .map {
                (it.filter { it.charId == selectedChar?.id })
            }

    fun addItem(item: Item) {
        viewModelScope.launch {
            itemRepository.insert(item)
        }
    }

    fun addSpell(spell: Spell) {
        viewModelScope.launch {
            spellRepository.insert(spell)
        }
    }

    fun editItem(item: Item) {
        viewModelScope.launch {
            itemRepository.update(item)
        }
    }

    fun editSpell(spell: Spell) {
        viewModelScope.launch {
            spellRepository.update(spell)
        }
    }

    fun removeItem(item: Item) {
        viewModelScope.launch {
            itemRepository.delete(item)
        }
    }

    fun removeSpell(spell: Spell) {
        viewModelScope.launch {
            spellRepository.delete(spell)
        }
    }

    fun onRemoveCharacter(id: Int) {
        viewModelScope.launch {
            itemRepository.allItems.map {
                (it.filter { it.charId == id })
            }.collect { it.forEach { itemRepository.delete(it) } }
        }
        viewModelScope.launch {
            spellRepository.allSpells.map {
                (it.filter { it.charId == id })
            }.collect { it.forEach { spellRepository.delete(it) } }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class EditViewModelFactory(private val itemRepository: ItemRepository,
    private val spellRepository: SpellRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditViewModel(itemRepository, spellRepository) as T
    }
}