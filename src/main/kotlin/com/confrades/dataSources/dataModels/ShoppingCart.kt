package com.confrades.dataSources.dataModels

data class ShoppingCart(
    var id: String,
    var userId: String,
    val items: ArrayList<ShoppingItem>,
    var qty: Int
)