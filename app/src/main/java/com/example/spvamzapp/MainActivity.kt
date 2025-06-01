package com.example.spvamzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.ui.theme.SPVAMZAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SPVAMZAppTheme {
                MainMenu()
            }
        }
    }
}

@Composable
fun MainMenu() {
    val layoutDirection = LocalLayoutDirection.current
    Surface(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(
            start = WindowInsets.safeDrawing.asPaddingValues()
                .calculateStartPadding(layoutDirection),
            end = WindowInsets.safeDrawing.asPaddingValues()
                .calculateEndPadding(layoutDirection))
        ){
        CharacterCardList(modifier = Modifier, mutableListOf(
            Character("Josh"),
            Character("Juan"),
            Character("Mauricio"),
            Character(),
            Character(),
            Character()
        ))

    }
}

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
            Text(text = character.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall)
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

@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    SPVAMZAppTheme {
        CharacterCard(modifier = Modifier,
            character = Character(name = "Lindsey Sterling"))
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    SPVAMZAppTheme {
        MainMenu()
    }
}