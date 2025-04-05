package br.com.erudio.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.model.Book;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.mockito.Mock;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setTitle("TitleTest" + number);
        book.setAuthor("AuthorTest" + number);
        book.setPrice(1000.00);
        book.setId(number.longValue());
        book.setLaunch_date(new Date());
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setTitle("TitleTest" + number);
        book.setAuthor("AuthorTest" + number);
        book.setPrice(1000.00);
        book.setId(number.longValue());
        book.setLaunch_date(new Date());
        return book;
    }

}