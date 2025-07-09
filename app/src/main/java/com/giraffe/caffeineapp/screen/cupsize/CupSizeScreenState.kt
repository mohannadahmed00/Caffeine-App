package com.giraffe.caffeineapp.screen.cupsize

data class CoffeeSizeScreenState(
    val selectedSize: CupSize = CupSize.SMALL,
    val selectedPercentage: CoffeePercentage = CoffeePercentage.LOW,
    val isCoffeePrepare: Boolean = false,
    val isCoffeeReady: Boolean = false,
)

enum class CupSize(val amount: Int) {
    SMALL(150),
    MEDIUM(200),
    LARGE(400),
}

enum class CoffeePercentage {
    LOW,
    MEDIUM,
    HIGH,
}