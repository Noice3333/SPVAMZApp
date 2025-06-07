package com.example.spvamzapp.spell

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spells")
data class Spell(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val charId: Int,
    var name: String = "",
    var description: String = ""
)
