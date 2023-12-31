package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookRepo bookRepo;
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try{
            List<Book> bookList=new ArrayList<>();
            bookRepo.findAll().forEach(bookList::add);

            if (bookList.isEmpty()){
                return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            return new ResponseEntity<>(bookList,HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getBooksById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
       Optional<Book> bookObj =bookRepo.findById(id);

       if(bookObj.isPresent()){
           return new ResponseEntity<>(bookObj.get(),HttpStatus.OK);
       }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        try {
            Book bookObj = bookRepo.save(book);
            return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book book){
        try {
            Optional<Book> bookData = bookRepo.findById(id);
            if (bookData.isPresent()) {
                Book updatedBookData = bookData.get();
                updatedBookData.setTitle(book.getTitle());
                updatedBookData.setAuthor(book.getAuthor());

                Book bookObj = bookRepo.save(updatedBookData);
                return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        try {
            bookRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteAllBooks")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            bookRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
