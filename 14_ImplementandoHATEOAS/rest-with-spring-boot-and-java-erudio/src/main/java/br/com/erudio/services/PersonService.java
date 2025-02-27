package br.com.erudio.services;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.erudio.mapper.ObjectMapper.parseObject;
import static br.com.erudio.mapper.ObjectMapper.parseListObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public List<PersonDTO> findAll() {

        logger.info("Fiding all Person!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {

        logger.info("Finding one Person!");

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID : " + id));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(id, dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person) {

        logger.info("Create one Person!");
        var entity = parseObject(person, Person.class);

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {

        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID : " + person.getId()));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID : " + id));
        repository.delete(person);
    }

    private static void addHateoasLinks(Long id, PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(id)).withRel("delete").withType("DELETE"));
    }
}
