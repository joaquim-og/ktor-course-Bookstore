package com.confrades.dataSources.dataModels

import org.bson.types.ObjectId

data class Book(
    var bookId: ObjectId?,
    var title: String,
    var author: String,
    var price: Float
) {
    constructor() : this(null, "not_set", "not_set", 0F){}
}