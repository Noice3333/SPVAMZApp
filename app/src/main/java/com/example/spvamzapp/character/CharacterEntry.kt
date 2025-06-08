package com.example.spvamzapp.character

import androidx.room.Entity
import androidx.room.PrimaryKey

//Dátová trieda predstavujúca postavu
//stringResource sa nedá použiť
@Entity(tableName = "characterEntries")
data class CharacterEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var charClass: String = "",
    var charRace: String = "",
    var charAlignment: String = "",
    var pictureURI: String? = null
)