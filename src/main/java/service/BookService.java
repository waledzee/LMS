package service;

import dto.BookDto;
import entity.Book;

import java.util.List;

public interface BookService {
    Book createBook(BookDto book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Long id, BookDto book);
    void deleteBook(Long id);
}

