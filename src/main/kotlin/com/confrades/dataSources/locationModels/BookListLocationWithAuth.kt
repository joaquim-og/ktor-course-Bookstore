package com.confrades.dataSources.locationModels

import io.ktor.locations.*

@Location("/api/list")
data class BookListLocationWithAuth(
    val sortBy: String,
    val asc: Boolean
)
