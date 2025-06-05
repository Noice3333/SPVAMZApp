package com.example.spvamzapp.character

import com.example.spvamzapp.item.Item
import com.example.spvamzapp.item.ItemDao
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: Flow<List<Item>> = itemDao.getAllItems()

    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }

    suspend fun update(item: Item) {
        itemDao.update(item)
    }

    suspend fun delete(item: Item) {
        itemDao.delete(item)
    }
}