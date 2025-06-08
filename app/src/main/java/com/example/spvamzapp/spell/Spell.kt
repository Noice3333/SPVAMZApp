package com.example.spvamzapp.spell

import androidx.room.Entity
import androidx.room.PrimaryKey

//Jedno kúzlo postavy
//stringResource sa nedá použiť
@Entity(tableName = "spells")
data class Spell(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val charId: Int,
    var name: String = "",
    var description: String = ""
)
