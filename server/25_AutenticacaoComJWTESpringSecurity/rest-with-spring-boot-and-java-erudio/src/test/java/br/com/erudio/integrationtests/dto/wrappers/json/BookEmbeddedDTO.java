package br.com.erudio.integrationtests.dto.wrappers.json;

import br.com.erudio.integrationtests.dto.BookDTO;
import br.com.erudio.integrationtests.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

public class BookEmbeddedDTO implements Serializable {
    private static final long serialVersion = 1L;

    @JsonPropertyOrder("books")
    private List<BookDTO> books;

    public BookEmbeddedDTO() {}

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
