package br.com.erudio.mapper.custom;

import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonMapper {

    public static PersonDTOV2 convertEntityToDTO(Person person) {
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(new Date());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        return dto;
    }

    public static Person convertDTOtoEntity(PersonDTOV2 dto) {
        Person entity = new Person();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAddress(dto.getAddress());
        entity.setGender(dto.getGender());
        return entity;
    }

    public static List<PersonDTOV2> convertListEntityToDTO(List<Person> persons) {
        List<PersonDTOV2> dtos = new ArrayList<>();
        for (Person person : persons) {
            PersonDTOV2 dto = new PersonDTOV2();
            dto.setId(person.getId());
            dto.setFirstName(person.getFirstName());
            dto.setLastName(person.getLastName());
            dto.setBirthDate(new Date());
            dto.setAddress(person.getAddress());
            dto.setGender(person.getGender());
            dtos.add(dto);
        }
        return dtos;
    }
}
