package com.example.spvamzapp.spell

import kotlinx.coroutines.flow.Flow

class SpellRepository(private val spellDao: SpellDao) {
    val allSpells: Flow<List<Spell>> = spellDao.getAllSpells()

    suspend fun insert(spell: Spell) {
        spellDao.insert(spell)
    }

    suspend fun update(spell: Spell) {
        spellDao.update(spell)
    }

    suspend fun delete(spell: Spell) {
        spellDao.delete(spell)
    }
}