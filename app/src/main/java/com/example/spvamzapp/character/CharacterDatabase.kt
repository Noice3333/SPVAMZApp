package com.example.spvamzapp.character

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spvamzapp.R

//Databáza postáv
@Database(entities = [CharacterEntry::class], version = 3, exportSchema = false)
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var Instance: CharacterDatabase? = null
        fun getDatabase(context: Context): CharacterDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CharacterDatabase::class.java,
                    context.getString(R.string.character_database_name))
                    .fallbackToDestructiveMigration(true).build().also { Instance = it }
            }
        }
    }
}