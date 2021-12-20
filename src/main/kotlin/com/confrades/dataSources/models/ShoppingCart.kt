package com.confrades.dataSources.models

data class ShoppingCart(
    var id: String,
    var userId: String,
    val items: ArrayList<ShoppingItem>,
    var qty: Int
)