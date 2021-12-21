package com.confrades.dataSources

import com.confrades.dataSources.dataModels.Book
import org.slf4j.LoggerFactory
import kotlin.reflect.full.declaredMemberProperties

class BooksDataManager {

    private var books = ArrayList<Book>()
    private val log = LoggerFactory.getLogger(BooksDataManager::class.java)

    init {
        books.add(Book(getBookId(), "Xablau livro 1", "Confrades Tech", 100F))
        books.add(Book(getBookId(), "Xablau livro 2", "Confrades Tech", 200F))
        books.add(Book(getBookId(), "Xablau livro 3", "Confrades Tech", 300F))
        books.add(Book(getBookId(), "Xablau livro 4", "Confrades Tech", 400F))
        books.add(Book(getBookId(), "Xablau livro 5", "Confrades Tech", 500F))
    }

    fun getAllBooks() = books

    fun newBook(newBook: Book) {
        books.add(newBook)
    }

    fun updateBook(book: Book): Book? {
        val bookToUpdate = findBook(book.id)

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
        it.id == bookId
    }

    private fun getBookId(): String = books.size.toString()

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

}