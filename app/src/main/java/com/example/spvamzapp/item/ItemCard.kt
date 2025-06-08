package com.example.spvamzapp.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.R
import com.example.spvamzapp.screens.RemovalDialog
import com.example.spvamzapp.viewmodels.EditViewModel

//Karta zobrazujúca názov a opis položky postavy
@Composable
fun ItemCard(modifier: Modifier = Modifier,
             edcm: EditViewModel,
             item: Item
)
{
    var textFieldEditable by rememberSaveable { mutableIntStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    name = item.name
    var description by rememberSaveable { mutableStateOf("") }
    description = item.description
    var showRemoveDialog by rememberSaveable { mutableStateOf(false) }
    if (showRemoveDialog == true) {
        RemovalDialog({showRemoveDialog = false},
            {edcm.removeItem(item)
            showRemoveDialog = false},
            stringResource(R.string.delete_dialog_title),
            stringResource(R.string.item_deletion_prompt),
            Icons.Filled.Delete)
    }
    Card(modifier
        .fillMaxWidth()
        .heightIn(100.dp)) {
        Column {
            Row {
                TextField(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 16.dp),
                    enabled = textFieldEditable == 1,
                    value = name,
                    onValueChange = { name = it },
                    textStyle = MaterialTheme.typography.headlineSmall,
                    singleLine = true
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
                        contentDescription = stringResource(R.string.edit_item_name_button_desc)
                    )
                }
            }
            Row {
                TextField(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 16.dp),
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
                        contentDescription = stringResource(R.string.edit_item_description_button_desc),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
            if (textFieldEditable == 1) {
                IconButton(onClick = {showRemoveDialog = true
                textFieldEditable = 0}) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_item_button_desc)
                    )
                }
            }
        }
    }
}
