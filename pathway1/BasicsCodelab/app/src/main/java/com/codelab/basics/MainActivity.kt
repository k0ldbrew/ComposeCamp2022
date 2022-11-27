package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

data class MainItem(val name: String, val content: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                KoldApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun KoldApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(
                onContinueClicked = { shouldShowOnboarding = false }
            )
        } else {
            Greetings()
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<MainItem> = List(1000) {
        MainItem(
            "$it",
            stringResource(id = R.string.composem_ipsum).repeat(3)
        )
    }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { item ->
            Greeting(name = item.name, contents = item.content)
        }
    }
}

@Composable
fun Greeting(
    name: String,
    contents: String
) {
    Card(
        colors = CardDefaults.cardColors(),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name, contents)
    }
}

@Composable
private fun CardContent(name: String, contents: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Hello, ")
            Text(text = name, style = MaterialTheme.typography.headlineMedium)
            if (expanded) {
                Text(text = contents, fontSize = 10.sp, lineHeight = 15.sp)
            }
        }
        IconButton(
            onClick = {
                expanded = !expanded
            }
        ) {
            val iconSet = if (expanded) {
                Icons.Filled.ExpandLess to stringResource(id = R.string.show_less)
            } else {
                Icons.Filled.ExpandMore to stringResource(id = R.string.show_more)
            }

            Icon(
                imageVector = iconSet.first,
                contentDescription = iconSet.second
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, name = "Text preview", widthDp = 320)
@Composable
fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreviewWithDarkMode() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        KoldApp(Modifier.fillMaxSize())
    }
}