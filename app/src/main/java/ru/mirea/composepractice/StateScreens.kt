package ru.mirea.composepractice

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun WaterTracker() {
    var waterCount by rememberSaveable { mutableIntStateOf(100) }
    var streak by rememberSaveable { mutableIntStateOf(0) }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val availableHeight = maxHeight
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).heightIn(min = availableHeight)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            Text("Трекер воды", style = MaterialTheme.typography.headlineMedium)
            Text("$waterCount мл", style = MaterialTheme.typography.displaySmall)
            Button(onClick = { waterCount += 250 }) { Text("+250 мл") }
            OutlinedButton(onClick = {
                streak = nextStreak(waterCount, streak)
                waterCount = 0
            }) { Text("Завершить день") }
            Text("Дней подряд с нормой 1500 мл: $streak")
        }
    }
}

private val cartSaver = listSaver<Cart, Int>(
    save = { cart -> cart.products.map { it.price } },
    restore = { prices -> Cart(prices.mapIndexed(::numberedProduct)) }
)

@Preview(showBackground = true)
@Composable
fun ShoppingCartScreen() {
    val context = LocalContext.current
    var cart by rememberSaveable(stateSaver = cartSaver) {
        mutableStateOf(Cart(listOf(100, 150, 56).mapIndexed(::numberedProduct)))
    }

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Товаров на сумму: ${cart.total} рублей", style = MaterialTheme.typography.titleMedium)
        Button(onClick = {
            val updated = cart.add(price = 97)
            val showFreeDelivery = qualifiesForFreeDelivery(cart, updated)
            cart = updated
            if (showFreeDelivery) {
                Toast.makeText(context, "Доставка бесплатная!", Toast.LENGTH_SHORT).show()
            }
        }) { Text("Добавить товар") }
        if (cart.products.isNotEmpty()) {
            OutlinedButton(onClick = { cart = cart.removeLast() }) { Text("Удалить товар") }
        }
        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(cart.products, key = { it.id }) { product ->
                Text("${product.name} — ${product.price} рублей")
            }
        }
    }
}
