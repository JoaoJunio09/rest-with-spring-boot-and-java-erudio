package br.com.erudio.services;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import static br.com.erudio.mapper.ObjectMapper.parseObject;
import static br.com.erudio.mapper.ObjectMapper.parseListObjects;

import java.util.List;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookDTO> findAll() {

        logger.info("Finds all Books!");

        var books = parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(this::addHeteoasLinks);
        return books;
    }

    public BookDTO findById(Long id) {

        logger.info("Find By ID Books!");

        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID : " + id));
        var dto = parseObject(book, BookDTO.class);
        addHeteoasLinks(dto);
        return dto;
    }

    public BookDTO create(BookDTO bookDTO) {

        if (bookDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Create one Book!");
        var entity = parseObject(bookDTO, Book.class);

        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHeteoasLinks(dto);
        return dto;
    }

    public BookDTO update(BookDTO bookDTO) {

        logger.info("Update Book!");

        Book entity = repository.findById(bookDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID : " + bookDTO.getId()));
        entity.setAuthor(bookDTO.getAuthor());
        entity.setLaunch_date(bookDTO.getLaunch_date());
        entity.setPrice(bookDTO.getPrice());
        entity.setTitle(bookDTO.getTitle());

        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHeteoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Delete Book!");

        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID : " + id));
        repository.delete(book);
    }

    private void addHeteoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
