package com.example.spvamzapp.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.R
import com.example.spvamzapp.viewmodels.EditViewModel
import com.example.spvamzapp.viewmodels.MainMenuViewModel

@Composable
fun CharacterCard(modifier: Modifier = Modifier,
                  character: CharacterEntry,
                  onEditButtonClicked: () -> Unit,
                  edcm: EditViewModel,
                  mmvm: MainMenuViewModel
) {
    Card(modifier = modifier.heightIn(50.dp, 100.dp)) {
        Box {
            Image(painter = painterResource(R.drawable.swordsmanmale),
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
                    .alpha(0.5f),
                contentScale = ContentScale.FillHeight)
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = character.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(0.9f),
                        style = MaterialTheme.typography.headlineSmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Button(
                        onClick = {
                            edcm.selectedChar = mmvm.mainMenuUiState.value.charList.find { it.id == character.id }
                            onEditButtonClicked()
                        }
                    )
                    { Text(text = stringResource(R.string.edit)) }
                }
                Text(
                    text = character.charRace,
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = character.charClass,
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}