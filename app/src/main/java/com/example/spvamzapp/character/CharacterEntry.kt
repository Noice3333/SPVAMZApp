package com.example.spvamzapp.character

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spvamzapp.R

@Entity(tableName = "characterEntries")
data class CharacterEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var charClass: String = "",
    var charRace: String = "",
    var charAlignment: String = "",
    @DrawableRes var pictureID: Int? = R.drawable.swordsmanmale
)