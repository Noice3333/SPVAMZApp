package com.example.spvamzapp.character

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spvamzapp.R
import com.example.spvamzapp.item.Item
import com.example.spvamzapp.item.ItemDao

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: ItemDatabase? = null
        fun getDatabase(context: Context): ItemDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ItemDatabase::class.java,
                    context.getString(R.string.item_database_name))
                    .fallbackToDestructiveMigration(true).build().also { Instance = it }
            }
        }
    }
}