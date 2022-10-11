package com.lucie.bookbroker2.repositories;

import com.lucie.bookbroker2.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
    List<Book> findByUserIdIs(Long userId);
    List<Book> findByBorrowerIdIs(Long userId);
    List<Book> findByBorrowerIdIsOrUserIdIs(Long borrowerID, Long userId);
}