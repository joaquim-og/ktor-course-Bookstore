package com.confrades.routes

import com.confrades.dataSources.BooksDataManager
import com.confrades.dataSources.dataModels.Book
import com.confrades.dataSources.dataModels.BookReserveResponse
import com.confrades.dataSources.dataModels.BookResponse
import com.confrades.dataSources.dataModels.HyperMediaLink
import com.confrades.dataSources.locationModels.BookListLocation
import com.confrades.dataSources.locationModels.BookListLocationWithAuth
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.get

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.books() {

    val booksDataManager = BooksDataManager()

    route("/book") {
        get<BookListLocation> {
            call.respond(booksDataManager.sortedBooks(it.sortBy, it.asc))
        }
        authenticate("bookStoreAuth") {
            get<BookListLocationWithAuth> {
                call.respond(booksDataManager.sortedBooks(it.sortBy, it.asc))
            }
        }
    }

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

        get("/{bookID}") {
            val bookId = call.parameters["bookID"]
            val bookToGet = booksDataManager.findBook(bookId)
            val hypermediaLink = listOf(
                HyperMediaLink(
                    "https://localhost:8080/books/$bookId/checkout",
                    "checkout", "GET"
                ),
                HyperMediaLink(
                    "https://localhost:8080/books/$bookId/reserve",
                    "reserve", "GET"
                )
            )

            val bookResponse = BookResponse(
                bookToGet ?: Book(id = "nopes", title = "livro 404", author = "Casper", price = 0F),
                hypermediaLink
            )
            call.respond(bookResponse)

        }

        get("/{bookId}/checkout") {
            call.respondText { "Checked the book ${call.parameters["bookId"]}" }
        }

        get("/{bookId}/reserve") {
            call.respond(BookReserveResponse("Reserved the book ${call.parameters["bookId"]}", emptyList()))
        }

    }
}