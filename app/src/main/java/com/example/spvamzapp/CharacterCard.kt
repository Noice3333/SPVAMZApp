package com.example.spvamzapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CharacterCard(modifier: Modifier = Modifier,
                  character: Character) {
    Card(modifier = modifier) {
        Column {
            Image(painter = painterResource(character.pictureID ?: R.drawable.swordsmanmale),
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.FillHeight)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = character.name,
                    modifier = Modifier.padding(16.dp).weight(0.9f),
                    style = MaterialTheme.typography.headlineSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Button(onClick = {}) { Text(text = stringResource(R.string.edit))}
            }
        }
    }
}

@Composable
fun CharacterCardList(modifier: Modifier = Modifier,
                      cardList: MutableList<Character>) {
    LazyColumn(modifier = modifier) {
        items(cardList.size) { character ->
            CharacterCard(Modifier.padding(8.dp), cardList[character])
        }
    }
}