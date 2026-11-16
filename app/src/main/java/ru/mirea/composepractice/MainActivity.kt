package ru.mirea.composepractice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PracticeMenu {
                    startActivity(Intent(this, HelloActivity::class.java))
                }
            }
        }
    }
}

class HelloActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    Box(Modifier.padding(16.dp)) {
                        Hello(null)
                    }
                }
            }
        }
    }
}

@Composable
private fun PracticeMenu(openHello: () -> Unit) {
    var task by rememberSaveable { mutableIntStateOf(0) }
    val labels = listOf(
        "1. Приветствие", "2. Макеты предпросмотра", "3. Оформление текста",
        "4. Кнопка", "5. Инициалы", "6. Векторный круг", "7. Column и Row",
        "8–9. Трекер воды и его тесты", "10. Корзина"
    )
    BackHandler(task != 0) { task = 0 }
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.padding(vertical = 12.dp)) {
            if (task == 0) {
                Column(
                    Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Практикум Compose", style = MaterialTheme.typography.headlineSmall)
                    labels.forEachIndexed { index, label ->
                        OutlinedButton(
                            onClick = { if (index == 0) openHello() else task = index },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(label) }
                    }
                }
            } else {
                TextButton(onClick = { task = 0 }) { Text("К заданиям") }
                Text(labels[task], Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                val horizontalPadding = if (task == 3 || task == 4) 0.dp else 16.dp
                Box(Modifier.weight(1f).fillMaxWidth().padding(horizontal = horizontalPadding)) {
                    when (task) {
                        1 -> Text("Три макета HelloPreview доступны в Android Studio.")
                        2 -> TextStyles()
                        3 -> StyledButton()
                        4 -> InitialsBanner()
                        5 -> Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            CornerCircle()
                            StretchedCircle()
                        }
                        6 -> ContactLayouts()
                        7 -> WaterTracker()
                        8 -> ShoppingCartScreen()
                    }
                }
            }
        }
    }
}
