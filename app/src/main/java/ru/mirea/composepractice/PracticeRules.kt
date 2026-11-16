package ru.mirea.composepractice

data class Product(val id: Int, val name: String, val price: Int)

data class Cart(val products: List<Product>) {
    val total: Int get() = products.sumOf { it.price }

    fun add(price: Int): Cart {
        return Cart(products + numberedProduct(products.size, price))
    }

    fun removeLast(): Cart = Cart(products.dropLast(1))
}

internal fun numberedProduct(index: Int, price: Int): Product {
    return Product(index, "Товар #${index + 1}", price)
}

internal fun qualifiesForFreeDelivery(previous: Cart, updated: Cart): Boolean {
    return previous.total <= 500 && updated.total > 500
}

internal fun nextStreak(waterCount: Int, streak: Int): Int {
    return if (waterCount >= 1500) streak + 1 else 0
}
