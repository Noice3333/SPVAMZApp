package com.example.spvamzapp.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.mainMenu.EditViewModel

@Composable
fun ItemCard(modifier: Modifier = Modifier,
             edcm: EditViewModel,
             item: Item
)
{
    var textFieldEditable by rememberSaveable { mutableIntStateOf(0) }
    var name by rememberSaveable { mutableStateOf(item.name) }
    var description by rememberSaveable { mutableStateOf(item.description) }
    Card(modifier) {
        Column(Modifier.heightIn(150.dp, 300.dp)) {
            Row(modifier = Modifier.weight(1f,true)) {
                TextField(
                    modifier = Modifier.weight(0.9f).padding(start = 16.dp),
                    enabled = textFieldEditable == 1,
                    value = name,
                    onValueChange = { name = it },
                    textStyle = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = {
                    if (textFieldEditable == 1) {
                        textFieldEditable = 0
                        item.name = name
                        edcm.editItem(item)
                    }
                    else {
                        textFieldEditable = 1
                    }
                },
                    modifier = Modifier.weight(0.1f))
                {
                    Icon(imageVector = Icons.Filled.Create,
                        contentDescription = "Edit item")
                }
            }
            Row(modifier = Modifier.weight(3f,true)) {
                TextField(
                    modifier = Modifier.weight(0.9f).padding(start = 16.dp),
                    enabled = textFieldEditable == 2,
                    value = description,
                    onValueChange = { description = it },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = {
                    if (textFieldEditable == 2) {
                        textFieldEditable = 0
                        item.description = description
                        edcm.editItem(item)
                    }
                    else {
                        textFieldEditable = 2
                    }
                }, modifier = Modifier.weight(0.1f)) {
                    Icon(imageVector = Icons.Default.Create,
                        contentDescription = "Edit item",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
        }
    }
}
