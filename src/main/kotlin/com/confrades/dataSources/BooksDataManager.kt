package com.confrades.dataSources

import com.confrades.dataSources.dataModels.Book
import com.confrades.dataSources.repository.MongoClients
import com.mongodb.client.MongoCollection
import org.slf4j.LoggerFactory
import com.mongodb.client.*
import kotlin.reflect.full.declaredMemberProperties

private val mongoDataHandler = MongoClients().getMongoClient()

class BooksDataManager {

    private var books = ArrayList<Book>()
    private val log = LoggerFactory.getLogger(BooksDataManager::class.java)

    private lateinit var booksCollection: MongoCollection<Book>

    init {
        initSomeFakeBookData(mongoDataHandler)
    }

    fun newBook(newBook: Book) {
        books.add(newBook)
    }

    fun updateBook(book: Book): Book? {
        val bookToUpdate = findBook(book.bookId.toString())

        bookToUpdate?.let {
            it.title = book.title
            it.author = book.author
            it.price = book.price
        }

        return bookToUpdate
    }

    fun deleteBook(bookId: String?) {
        val bookToDelete = findBook(bookId)

        books.remove(bookToDelete)
    }

    fun findBook(bookId: String?): Book? = books.find {
        it.bookId.toString() == bookId
    }

    fun sortedBooks(sortBy: String, asc: Boolean): List<Book> {
        val member = Book::class.declaredMemberProperties.find { it.name == sortBy }

        if (member == null) {
            log.info("The param to sort doesnt exist")
            return books
        }

        return if (asc) {
            books.sortedBy { member.get(it).toString() }
        } else {
            books.sortedByDescending { member.get(it).toString() }
        }

    }

    fun getAllBooks(): List<Book> {
        val mongoResult = booksCollection.find()
        mongoResult.forEach {
            println("Here the book -> ${it.bookId} | ${it.author} | ${it.price} | ${it.title}")
        }

        return mongoResult.toList()
    }

    private fun initSomeFakeBookData(mongoClients: MongoClient) {
        val database = mongoClients.getDatabase("development")
        booksCollection = database.getCollection(Book::class.java.name, Book::class.java)

        booksCollection.insertOne(Book(null, "Xablau livro 1", "Confrades Tech", 100F))
        booksCollection.insertOne(Book(null, "Xablau livro 2", "Confrades Tech", 200F))
        booksCollection.insertOne(Book(null, "Xablau livro 3", "Confrades Tech", 300F))
        booksCollection.insertOne(Book(null, "Xablau livro 4", "Confrades Tech", 400F))
        booksCollection.insertOne(Book(null, "Xablau livro 5", "Confrades Tech", 500F))
    }

}