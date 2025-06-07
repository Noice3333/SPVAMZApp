package com.example.spvamzapp.spell

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SpellDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(spell: Spell)

    @Update
    suspend fun update(spell: Spell)

    @Delete
    suspend fun delete(spell: Spell)

    @Query("SELECT * FROM spells")
    fun getAllSpells(): Flow<List<Spell>>
}