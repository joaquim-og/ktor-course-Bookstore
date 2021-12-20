package com.confrades.dataSources

import com.confrades.dataSources.models.Book

class BooksDataManager {

    private var books = ArrayList<Book>()

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

    private fun getBookId(): String = books.size.toString()

    private fun findBook(bookId: String?): Book? = books.find {
        it.id == bookId
    }

}