package com.example.spvamzapp.item

import androidx.room.Entity
import androidx.room.PrimaryKey

//Jedna položka prislúchajúca postave
//stringResource sa nedá použiť
@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val charId: Int,
    var name: String = "",
    var description: String = ""
)
