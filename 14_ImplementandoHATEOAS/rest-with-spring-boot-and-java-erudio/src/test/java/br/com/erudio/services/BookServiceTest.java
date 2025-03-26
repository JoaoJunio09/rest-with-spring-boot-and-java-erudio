package br.com.erudio.services;

import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private MockBook input;

    @Mock
    BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUpt() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Book> books = input.mockEntityList();
        when(repository.findAll()).thenReturn(books);

        var result = service.findAll();

        assertNotNull(result);
        assertEquals(14, result.size());

        var bookOne = result.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getLinks());
        assertNotNull(bookOne.getId());

        bookOne.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().endsWith("/api/book/v1/1")
                && link.getType().equals("GET")
            );

        bookOne.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("findALl")
                    && link.getHref().endsWith("/api/book/v1")
                    && link.getType().equals("GET")
            );

        bookOne.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("create")
                    && link.getHref().endsWith("/api/book/v1")
                    && link.getType().equals("POST")
            );

        bookOne.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("update")
                    && link.getHref().endsWith("/api/book/v1")
                    && link.getType().equals("PUT")
            );

        bookOne.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("delete")
                    && link.getHref().endsWith("/api/book/v1/1")
                    && link.getType().equals("DELETE")
            );

        assertEquals("Title Test1", bookOne.getTitle());
        assertEquals("Author Test1", bookOne.getAuthor());
        assertEquals(1000.00, bookOne.getPrice());

        var bookSeven = result.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getLinks());
        assertNotNull(bookSeven.getId());

        bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/7")
                        && link.getType().equals("GET")
                );

        bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findALl")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                );

        bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                );

        bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                );

        bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/7")
                        && link.getType().equals("DELETE")
                );

        assertEquals("Title Test7", bookSeven.getTitle());
        assertEquals("Author Test7", bookSeven.getAuthor());
        assertEquals(1000.00, bookSeven.getPrice());
    }

    @Test
    void findById() {
        Book book = input.mockEntity();
        book.setId(1L);
        when(repository.findById(book.getId())).thenReturn(Optional.of(book));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getId());

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findALl")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                );

        assertEquals("Title Test0", result.getTitle());
        assertEquals("Author Test0", result.getAuthor());
        assertEquals(1000.00, result.getPrice());
    }

    @Test
    void create() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.save(book)).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getId());

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findALl")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                );

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                );

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(1000.00, result.getPrice());
    }

    @Test
    void update() {
        Book book = input.mockEntity();
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getId());

        result.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("self")
                    && link.getHref().endsWith("/api/book/v1/1")
                    && link.getType().equals("GET")
            );

        result.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("findALl")
                    && link.getHref().endsWith("/api/book/v1")
                    && link.getType().equals("GET")
            );

        result.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("create")
                    && link.getHref().endsWith("/api/book/v1")
                    && link.getType().equals("POST")
            );

        result.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("update")
                    && link.getHref().endsWith("/api/book/v1")
                    && link.getType().equals("PUT")
            );

        result.getLinks().stream()
            .anyMatch(link -> link.getRel().value().equals("delete")
                    && link.getHref().endsWith("/api/book/v1/1")
                    && link.getType().equals("DELETE")
            );

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(1000.00, result.getPrice());
    }

    @Test
    void delete() {
        Book book = input.mockEntity();
        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }
}
