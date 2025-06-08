package com.example.spvamzapp.character

import kotlinx.coroutines.flow.Flow

//Repozitár na postavy
class CharacterRepository(private val characterDao: CharacterDao) {
    val allCharacters: Flow<List<CharacterEntry>> = characterDao.getAllCharacters()

    suspend fun insert(newChar: CharacterEntry) {
        characterDao.insert(newChar)
    }

    suspend fun update(characterEntry: CharacterEntry) {
        characterDao.update(characterEntry)
    }

    suspend fun delete(characterEntry: CharacterEntry) {
        characterDao.delete(characterEntry)
    }
}