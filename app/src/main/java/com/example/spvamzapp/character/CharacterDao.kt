package com.example.spvamzapp.character

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Dao pre interakciu s repozitárom postáv
@Dao
interface CharacterDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characterEntry: CharacterEntry)

    @Update
    suspend fun update(characterEntry: CharacterEntry)

    @Delete
    suspend fun delete(characterEntry: CharacterEntry)

    @Query("SELECT * FROM characterEntries")
    fun getAllCharacters(): Flow<List<CharacterEntry>>
}