package com.confrades.dataSources.locationModels

import io.ktor.locations.*

@Location("/list")
data class BookListLocation(
    val sortBy: String,
    val asc: Boolean
)
