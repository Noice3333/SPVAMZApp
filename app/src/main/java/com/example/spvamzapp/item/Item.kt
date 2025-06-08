package com.example.spvamzapp.item

import androidx.room.Entity
import androidx.room.PrimaryKey

//stringResource is unusable outside of a composable
@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val charId: Int,
    var name: String = "",
    var description: String = ""
)
