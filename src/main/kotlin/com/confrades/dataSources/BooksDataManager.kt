package com.confrades.dataSources

import com.confrades.dataSources.dataModels.Book
import com.confrades.dataSources.repository.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.*
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import org.bson.types.ObjectId

private val mongoDataHandler = MongoClients().getMongoClient()

class BooksDataManager {

    private lateinit var booksCollection: MongoCollection<Book>

    init {
        initSomeFakeBookData(mongoDataHandler)
    }

    fun newBook(newBook: Book) {
        booksCollection.insertOne(newBook)
    }

    fun updateBook(book: Book): Book? {
        val bookToUpdate = booksCollection.find(Document("_id", book.bookId)).first()

        bookToUpdate?.let {
            it.title = book.title
            it.author = book.author
            it.price = book.price
        }

        return bookToUpdate
    }

    fun deleteBook(bookId: String?) {
        val bookToDelete = booksCollection.find(eq("_id", bookId)).first()

        booksCollection.deleteOne(eq("_id", ObjectId(bookId)))
    }

    fun findBook(bookId: String?): Book? {
        return booksCollection
            .find(eq("_id", bookId)).first()
    }


    fun sortedBooks(sortBy: String, asc: Boolean): List<Book> {
        val pageNo = 1
        val pageSize = 2
        val ascInt = if (asc) 1 else -1

        return booksCollection
            .find()
            .sort(Document(mapOf(Pair(sortBy, ascInt), Pair("_id", -1))))
            .skip(pageNo - 1)
            .limit(pageSize)
            .toList()

    }

    fun getAllBooks(): List<Book> {
        val mongoResult = booksCollection.find()
        mongoResult.forEach {
            println("Here the book -> $it")
        }

        return mongoResult.toList()
    }

    private fun initSomeFakeBookData(mongoClients: MongoClient) {
        val database = mongoClients.getDatabase("development")
        booksCollection = database.getCollection(Book::class.java.name, Book::class.java)
        deleteAllBooks()

        booksCollection.insertOne(Book(null, "Xablau livro 1", "Confrades Tech", 100F))
        booksCollection.insertOne(Book(null, "Xablau livro 2", "Confrades Tech", 200F))
        booksCollection.insertOne(Book(null, "Xablau livro 3", "Confrades Tech", 300F))
        booksCollection.insertOne(Book(null, "Xablau livro 4", "Confrades Tech", 400F))
        booksCollection.insertOne(Book(null, "Xablau livro 5", "Confrades Tech", 500F))
    }

    private fun deleteAllBooks() {
        booksCollection.deleteMany(Document())
    }
}