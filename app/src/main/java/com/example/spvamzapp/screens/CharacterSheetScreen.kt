package com.example.spvamzapp.screens

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.spvamzapp.R
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.character.CharacterImage
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
                title = { Text(stringResource(R.string.character_sheet_screen_title)) },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = {onBackButtonClicked()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_desc)
                        )
                    }
                },
                actions = {
                    if (selectedNavBarIcon == 0) {
                        IconButton(onClick = { editing = !editing }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = stringResource(R.string.toggle_editing_button_desc)
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
                            contentDescription = stringResource(R.string.character_details_navbar_desc)
                        )
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
                            contentDescription = stringResource(R.string.item_details_navbar_desc)
                        )
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
                            contentDescription = stringResource(R.string.spell_details_navbar_desc)
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = selectedNavBarIcon,
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
    val innerModifier = Modifier.padding(4.dp)
    var characterName by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.name) }
    var characterRace by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.charRace) }
    var characterClass by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.charClass) }
    var characterAlignment by rememberSaveable {
        mutableStateOf(edcm.selectedChar?.charAlignment) }
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    var image by rememberSaveable { mutableStateOf<Uri?>(
        edcm.selectedChar?.pictureURI?.toUri()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { result ->
        image = result
        edcm.selectedChar?.pictureURI = image.toString()
        mmvm.editCharacter(edcm.selectedChar ?: CharacterEntry())
    }
    val launcher2 = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
    }
    val context = LocalContext.current

    if (showAlertDialog) {
        RemovalDialog({showAlertDialog = false},
            {
                var id: Int = edcm.selectedChar?.id ?: 0
                mmvm.removeCharacter(edcm.selectedChar ?: CharacterEntry())
                edcm.onRemoveCharacter(id)
                onCancelButtonClick()
            },
            stringResource(R.string.delete_dialog_title),
            stringResource(R.string.character_deletion_prompt),
            Icons.Filled.Delete
            )
    }
    Box(modifier = modifier.fillMaxSize()) {
        CharacterImage(edcm.selectedChar ?: CharacterEntry(), 0.3f)
        Column(innerModifier) {
            Box(Modifier
                .heightIn(84.dp)
                .padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = stringResource(R.string.name_colon)
                )
                OutlinedTextField(
                    readOnly = !editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterName ?: "",
                    onValueChange = { characterName = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    textStyle = MaterialTheme.typography
                        .bodyMedium.copy(textAlign = TextAlign.End),
                    singleLine = true,
                )
            }
            Box(Modifier
                .heightIn(84.dp)
                .padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = stringResource(R.string.race_colon)
                )
                OutlinedTextField(
                    readOnly = !editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterRace ?: "",
                    onValueChange = { characterRace = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    textStyle = MaterialTheme.typography
                        .bodyMedium.copy(textAlign = TextAlign.End),
                    singleLine = true
                )
            }
            Box(Modifier
                .heightIn(84.dp)
                .padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = stringResource(R.string.class_colon)
                )
                OutlinedTextField(
                    readOnly = !editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterClass ?: "",
                    onValueChange = { characterClass = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    textStyle = MaterialTheme.typography
                        .bodyMedium.copy(textAlign = TextAlign.End),
                    singleLine = true
                )
            }
            Box(Modifier
                .heightIn(84.dp)
                .padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = stringResource(R.string.alignment_colon)
                )
                OutlinedTextField(
                    readOnly = !editing,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    value = characterAlignment ?: "",
                    onValueChange = { characterAlignment = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    textStyle = MaterialTheme.typography
                        .bodyMedium.copy(textAlign = TextAlign.End),
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
                    }) { Text(text = stringResource(R.string.save_button)) }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                showAlertDialog = true
            },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        )
        {
            Icon(
                imageVector = Icons.Filled.Delete, contentDescription = "",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }
        FloatingActionButton(
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) -> {
                        //This is a sad attempt at requesting permissions
                    }
                    else -> {
                        launcher2.launch(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
                        launcher2.launch(
                            android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                        )
                    }
                }
                launcher.launch(PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
        {
            Icon(
                imageVector = Icons.Filled.Face, contentDescription = "",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
fun EditItems(modifier: Modifier = Modifier,
              edcm: EditViewModel
) {
    val myFlow = edcm.itemFlowList.collectAsState(listOf()).value
    Box(modifier.fillMaxWidth()) {
        LazyColumn(contentPadding = PaddingValues(bottom = 50.dp)) {
            items(myFlow.size) { item ->
                ItemCard(Modifier.padding(4.dp), edcm, myFlow[item])
            }
        }
        IconButton(modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                edcm.addItem(Item(charId = edcm.selectedChar?.id ?: 0))
            }
        ) { Icon(imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_blank_item_button_desc)
        )}
    }
}

@Composable
fun EditSpells(modifier: Modifier = Modifier,
               edcm: EditViewModel) {
    val myFlow = edcm.spellFlowList.collectAsState(listOf()).value
    Box(modifier.fillMaxWidth()) {
        LazyColumn(contentPadding = PaddingValues(bottom = 50.dp)) {
            items(myFlow.size) { spell ->
                SpellCard(Modifier.padding(4.dp), edcm, myFlow[spell])
            }
        }
        IconButton(modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                edcm.addSpell(Spell(charId = edcm.selectedChar?.id ?: 0))
            }
        ) { Icon(imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_blank_spell_button_desc)
        )}
    }
}

@Composable
fun RemovalDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon,
            contentDescription = stringResource(R.string.removal_dialog_icon_desc)) },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText,
                textAlign = TextAlign.Center) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }
            ) { Text(stringResource(R.string.confirm_button_text)) }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }
            ) { Text(stringResource(R.string.dismiss_button_text)) }
        }
    )
}