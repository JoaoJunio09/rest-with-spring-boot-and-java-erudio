package br.com.erudio.integrationtests.dto.wrappers.json;

import br.com.erudio.integrationtests.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedDTO implements Serializable {
    private static final long serialVersion = 1L;

    @JsonPropertyOrder("people")
    private List<PersonDTO> people;

    public PersonEmbeddedDTO() {}

    public List<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonDTO> people) {
        this.people = people;
    }
}
