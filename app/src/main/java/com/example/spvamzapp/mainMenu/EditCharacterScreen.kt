package com.example.spvamzapp.mainMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.item.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCharacterScreen(
    mmvm: MainMenuViewModel,
    edcm: EditViewModel,
    onBackButtonClicked: () -> Unit
) {
    val itemUiState by edcm.editUiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("Edit character") },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = {onBackButtonClicked()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Return to main menu"
                        )
                    }
                }
            )
        }
    ) { innerPadding -> EditCharacter(Modifier.padding(innerPadding), mmvm,
        edcm,
        itemUiState.itemList,
        {onBackButtonClicked()}) }
}

@Composable
fun EditCharacter(modifier: Modifier = Modifier, mmvm: MainMenuViewModel,
                  edcm: EditViewModel,
                  list: List<Item>,
                  onCancelButtonClick: () -> Unit) {
    var characterName by rememberSaveable { mutableStateOf(edcm.selectedChar?.name) }
    val innerModifier = Modifier.padding(4.dp)

    Box(modifier = modifier.fillMaxSize()) {
        Column(innerModifier) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Character name:") },
                value = edcm.selectedChar?.name ?: "",
                onValueChange = { characterName = it }
            )
            Row(innerModifier) {
                Button(onClick = { onCancelButtonClick() }, Modifier) { Text(text = "Cancel") }
                Spacer(Modifier.weight(4f))
                Button(onClick = {
                    edcm.selectedChar?.name = characterName ?: ""
                    mmvm.editCharacter(edcm.selectedChar ?: CharacterEntry())
                    onCancelButtonClick()
                }) { Text(text = "Save") }
                Button(onClick = {
                    edcm.addItem(Item(charId = edcm.selectedChar?.id ?: 0))
                }) { Text(text = "Add blank item")}
            }
            LazyColumn {
                items(list.size) { item ->
                    list[item].name = "Wuju"
                    edcm.editItem(list[item])
                    Text(text = list[item].charId.toString())
                }
            }
        }
        FloatingActionButton(
            onClick = {
                mmvm.removeCharacter(edcm.selectedChar ?: CharacterEntry())
                onCancelButtonClick()
                      },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        { Icon(imageVector = Icons.Filled.Clear, contentDescription = "",
            tint = MaterialTheme.colorScheme.onErrorContainer) }
    }
}