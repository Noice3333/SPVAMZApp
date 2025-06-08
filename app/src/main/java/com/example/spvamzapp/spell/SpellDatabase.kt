package com.example.spvamzapp.spell

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spvamzapp.R

@Database(entities = [Spell::class], version = 1, exportSchema = false)
abstract class SpellDatabase: RoomDatabase() {
    abstract fun spellDao(): SpellDao

    companion object {
        @Volatile
        private var Instance: SpellDatabase? = null
        fun getDatabase(context: Context): SpellDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SpellDatabase::class.java,
                    context.getString(R.string.spell_database_name))
                    .fallbackToDestructiveMigration(true).build().also { Instance = it }
            }
        }
    }
}