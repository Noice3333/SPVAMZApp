package com.example.spvamzapp.character

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.spvamzapp.R
import com.example.spvamzapp.viewmodels.EditViewModel
import com.example.spvamzapp.viewmodels.MainMenuViewModel

//Karta, ktorá zobrazuje zhrnutie informácii o postave
@Composable
fun CharacterCard(modifier: Modifier = Modifier,
                  character: CharacterEntry,
                  onEditButtonClicked: () -> Unit,
                  edcm: EditViewModel,
                  mmvm: MainMenuViewModel
) {
    Card(modifier = modifier.heightIn(50.dp, 150.dp)) {
        Box {
            CharacterImage(character, 0.5f)
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = character.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(0.9f),
                        style = MaterialTheme.typography.headlineMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Button(
                        onClick = {
                            edcm.selectedChar = mmvm.mainMenuUiState.value.charList.find { it.id == character.id }
                            onEditButtonClicked()
                        }
                    )
                    { Text(text = stringResource(R.string.details)) }
                }
                Text(
                    text = character.charRace,
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = character.charClass,
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

//Táto funkcia zobrazuje obrázok, ktorý prislúcha postave podľa jednej z jej charakteristík
//alebo obrázok, ktorý si používateľ zvolí neskôr
@Composable
fun CharacterImage(character: CharacterEntry, alpha: Float) {
    var chClass by remember { mutableStateOf(character.charClass) }
    if (character.pictureURI != stringResource(R.string.nullString) && character.pictureURI != null) {
        AsyncImage(
            model = character.pictureURI?.toUri(),
            placeholder = painterResource(R.drawable.blank),
            contentDescription = stringResource(R.string.character_image_content_desc),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .padding(4.dp)
        )
    } else {
        var imgIndex: Int = -1

        stringArrayResource(R.array.characterClasses).forEachIndexed {
            index, thing ->
            if (chClass == thing) {
                imgIndex = index
            }
        }
        @DrawableRes val painterTarget: Int = when (imgIndex) {
            0 -> R.drawable.barbarian
            1 -> R.drawable.bard
            2 -> R.drawable.cleric
            3 -> R.drawable.druid
            4 -> R.drawable.fighter
            5 -> R.drawable.monk
            6 -> R.drawable.paladin
            7 -> R.drawable.ranger
            8 -> R.drawable.rogue
            9 -> R.drawable.sorcerer
            10 -> R.drawable.warlock
            11 -> R.drawable.wizard
            else -> R.drawable.blank
        }
        Image(
            painter = painterResource(painterTarget),
            contentDescription = stringResource(R.string.character_image_content_desc),
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .padding(4.dp),
            contentScale = ContentScale.FillHeight
        )
    }
}