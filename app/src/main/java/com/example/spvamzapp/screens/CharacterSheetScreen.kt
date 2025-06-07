package com.example.spvamzapp.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.R
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.item.Item
import com.example.spvamzapp.item.ItemCard
import com.example.spvamzapp.spell.Spell
import com.example.spvamzapp.spell.SpellCard
import com.example.spvamzapp.viewmodels.EditViewModel
import com.example.spvamzapp.viewmodels.MainMenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSheetScreen(
    mmvm: MainMenuViewModel,
    edcm: EditViewModel,
    onBackButtonClicked: () -> Unit
) {
    var selectedNavBarIcon by rememberSaveable { mutableIntStateOf(0) }
    var editing by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("Character sheet") },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = {onBackButtonClicked()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Return to main menu"
                        )
                    }
                },
                actions = {
                    if (selectedNavBarIcon == 0) {
                        IconButton(onClick = { editing = !editing }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "Return to main menu"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedNavBarIcon == 0,
                    onClick = {
                        if (selectedNavBarIcon != 0) {
                            selectedNavBarIcon = 0
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Person,
                            contentDescription = "Character details")
                    }
                )
                NavigationBarItem(
                    selected = selectedNavBarIcon == 1,
                    onClick = {
                        if (selectedNavBarIcon != 1) {
                            selectedNavBarIcon = 1
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.MailOutline,
                            contentDescription = "Item details")
                    }
                )
                NavigationBarItem(
                    selected = selectedNavBarIcon == 2,
                    onClick = {
                        if (selectedNavBarIcon != 2) {
                            selectedNavBarIcon = 2
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Star,
                            contentDescription = "Spell details")
                    }
                )
            }
        }
    ) { innerPadding ->
        AnimatedContent(targetState = selectedNavBarIcon,
        ) { targetState ->
            when (targetState) {
                0 -> {
                    EditCharacter(
                        Modifier.padding(innerPadding),
                        mmvm, edcm,
                        { onBackButtonClicked() },
                        editing)
                }

                1 -> {
                    EditItems(
                        Modifier.padding(innerPadding),
                        edcm
                    )
                    editing = false
                }

                2 -> {
                    EditSpells(
                        Modifier.padding(innerPadding),
                        edcm
                    )
                    editing = false
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCharacter(modifier: Modifier = Modifier, mmvm: MainMenuViewModel,
                  edcm: EditViewModel,
                  onCancelButtonClick: () -> Unit,
                  editing: Boolean) {
    var characterName by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.name) }
    var characterRace by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.charRace) }
    var characterClass by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.charClass) }
    var characterAlignment by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.charAlignment) }
    val innerModifier = Modifier.padding(4.dp)
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    var characterImage by rememberSaveable { mutableIntStateOf(
        edcm.selectedChar?.pictureID ?: R.drawable.swordsmanmale) }

    if (showAlertDialog) {
        RemoveCharacterDialog({showAlertDialog = false},
            {
                var id: Int = edcm.selectedChar?.id ?: 0
                mmvm.removeCharacter(edcm.selectedChar ?: CharacterEntry())
                edcm.onRemoveCharacter(id)
                onCancelButtonClick()
            },
            "Are you sure you want to delete this character?",
            "Delete",
            Icons.Filled.Star
            )
    }
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(characterImage),
            modifier = Modifier.alpha(0.3f).fillMaxSize(),
            contentDescription = "Character image"
        )
        Column(innerModifier) {
            Box(Modifier.heightIn(84.dp).padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = "Name:"
                )
                OutlinedTextField(
                    enabled = editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterName ?: "",
                    onValueChange = { characterName = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }
            Box(Modifier.heightIn(84.dp).padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = "Race:"
                )
                OutlinedTextField(
                    enabled = editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterRace ?: "",
                    onValueChange = { characterRace = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }
            Box(Modifier.heightIn(84.dp).padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = "Class:"
                )
                OutlinedTextField(
                    enabled = editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterClass ?: "",
                    onValueChange = { characterClass = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }
            Box(Modifier.heightIn(84.dp).padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = "Alignment:"
                )
                OutlinedTextField(
                    enabled = editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterAlignment ?: "",
                    onValueChange = { characterAlignment = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }
            if (editing) {
                Row(innerModifier) {
                    Spacer(Modifier.weight(4f))
                    Button(onClick = {
                        edcm.selectedChar?.name = characterName ?: ""
                        edcm.selectedChar?.charRace = characterRace ?: ""
                        edcm.selectedChar?.charClass = characterClass ?: ""
                        edcm.selectedChar?.charAlignment = characterAlignment ?: ""
                        mmvm.editCharacter(edcm.selectedChar ?: CharacterEntry())
                    }) { Text(text = "Save") }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                showAlertDialog = true
            },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
        )
        {
            Icon(
                imageVector = Icons.Filled.Clear, contentDescription = "",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
fun EditItems(modifier: Modifier = Modifier,
              edcm: EditViewModel
) {
    val myFlow = edcm.itemFlowList.collectAsState(listOf())
    Column(modifier.fillMaxWidth()) {
        LazyColumn() {
            items(myFlow.value.size) { item ->
                ItemCard(Modifier.padding(4.dp), edcm, myFlow.value[item])
            }
        }
        IconButton(modifier = Modifier.align(Alignment.End),
            onClick = {
                edcm.addItem(Item(charId = edcm.selectedChar?.id ?: 0))
            }
        ) { Icon(imageVector = Icons.Filled.Add,
            contentDescription = "Add blank item")}
    }
}

@Composable
fun EditSpells(modifier: Modifier = Modifier,
               edcm: EditViewModel) {
    val myFlow = edcm.spellFlowList.collectAsState(listOf())
    Column(modifier) {
        Button(onClick = {
            edcm.addSpell(Spell(charId = edcm.selectedChar?.id ?: 0))
        }) { Text(text = "Add blank item")}
        LazyColumn {
            items(myFlow.value.size) { spell ->
                SpellCard(Modifier.padding(4.dp), edcm, myFlow.value[spell])
            }
        }
    }
}

@Composable
fun RemoveCharacterDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}