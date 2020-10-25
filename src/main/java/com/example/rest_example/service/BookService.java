package com.example.rest_example.service;

import com.example.rest_example.exception.ResourceNotFoundException;
import com.example.rest_example.model.Book;
import com.example.rest_example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(int id) {
        bookRepository.delete(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book dose nor exists")));
    }
}
