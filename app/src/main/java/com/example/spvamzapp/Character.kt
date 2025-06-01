package com.example.spvamzapp

import androidx.annotation.DrawableRes

data class Character(
    var name: String = "",
    var charClass: String = "",
    var charRace: String = "",
    var items: MutableList<String> = mutableListOf<String>(),
    @DrawableRes var pictureID: Int? = R.drawable.swordsmanmale
)
