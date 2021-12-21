package com.confrades.dataSources.dataModels

data class BookResponse(
    val book: Book?,
    val links: List<HyperMediaLink>?
)
