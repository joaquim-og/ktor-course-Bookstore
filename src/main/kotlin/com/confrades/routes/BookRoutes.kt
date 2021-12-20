package com.confrades.routes

import com.confrades.dataSources.BooksDataManager
import com.confrades.dataSources.models.Book
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.books() {

    val booksDataManager = BooksDataManager()

    route("/books") {

        get("/") {
            call.respond(booksDataManager.getAllBooks())
        }

        post("/{id}") {
            val id = call.parameters["id"]
            val book = call.receive(Book::class)
            val bookUpdated = booksDataManager.updateBook(book)
            call.respondText { "The book ahs been update $bookUpdated" }
        }

        put("/") {
            val book = call.receive(Book::class)
            val newBook = booksDataManager.newBook(book)
            call.respondText { "The book $newBook has been created" }
        }

        delete("/{id}") {
            val id = call.parameters["id"]
            booksDataManager.deleteBook(id)
            call.respondText { "book deleted successfully" }
        }

    }

}