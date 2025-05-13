package br.com.erudio.integrationtests.controllers.withjson;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integrationtests.dto.BookDTO;
import br.com.erudio.integrationtests.dto.PersonDTO;
import br.com.erudio.integrationtests.dto.wrappers.json.WrapperBookDTO;
import br.com.erudio.integrationtests.dto.wrappers.json.WrapperPersonDTO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
            .setBasePath("/api/person/v1")
            .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content = given(specification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
            .when()
                .post()
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdBook = objectMapper.readValue(content, PersonDTO.class);
        person = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Linus", createdBook.getFirstName());
        assertEquals("Torvalds", createdBook.getLastName());
        assertEquals("Helsinki - Finland", createdBook.getAddress());
        assertEquals("Male", createdBook.getGender());
        assertTrue(createdBook.getEnabled());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setLastName("Benedict Torvalds");

        var content = given(specification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
            .when()
                .put()
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdBook = objectMapper.readValue(content, PersonDTO.class);
        person = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Linus", createdBook.getFirstName());
        assertEquals("Benedict Torvalds", createdBook.getLastName());
        assertEquals("Helsinki - Finland", createdBook.getAddress());
        assertEquals("Male", createdBook.getGender());
        assertTrue(createdBook.getEnabled());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdBook = objectMapper.readValue(content, PersonDTO.class);
        person = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Linus", createdBook.getFirstName());
        assertEquals("Benedict Torvalds", createdBook.getLastName());
        assertEquals("Helsinki - Finland", createdBook.getAddress());
        assertEquals("Male", createdBook.getGender());
        assertTrue(createdBook.getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var content = given(specification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
            .when()
                .patch("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdBook = objectMapper.readValue(content, PersonDTO.class);
        person = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Linus", createdBook.getFirstName());
        assertEquals("Benedict Torvalds", createdBook.getLastName());
        assertEquals("Helsinki - Finland", createdBook.getAddress());
        assertEquals("Male", createdBook.getGender());
        assertFalse(createdBook.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {

        given(specification)
            .pathParam("id", person.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
            .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParams("page", 3, "size", 12, "direction", "asc")
            .when()
                .get()
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
                .body()
                    .asString();

        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> people = wrapper.getEmbedded().getPeople();

        PersonDTO personOne = people.get(0);
        person = personOne;

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Allin", personOne.getFirstName());
        assertEquals("Otridge", personOne.getLastName());
        assertEquals("09846 Independence Center", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertFalse(personOne.getEnabled());

        PersonDTO personTwo = people.get(2);
        person = personTwo;

        assertNotNull(personTwo.getId());
        assertTrue(personTwo.getId() > 0);

        assertEquals("Allyn", personTwo.getFirstName());
        assertEquals("Josh", personTwo.getLastName());
        assertEquals("119 Declaration Lane", personTwo.getAddress());
        assertEquals("Female", personTwo.getGender());
        assertFalse(personTwo.getEnabled());
    }

    @Test
    @Order(7)
    void findByNameTest() throws JsonProcessingException {

        //{{baseUrl}}/api/person/v1/findPeopleByName/and?page=0&size=12&direction=asc
        var content = given(specification)
            .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("firstName", "and")
                .queryParams("page", 0, "size", 12, "direction", "asc")
            .when()
                .get("findPeopleByName/{firstName}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
                .body()
                    .asString();

        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> people = wrapper.getEmbedded().getPeople();

        PersonDTO personOne = people.get(0);
        person = personOne;

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Alessandro", personOne.getFirstName());
        assertEquals("McFaul", personOne.getLastName());
        assertEquals("5 Lukken Plaza", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertFalse(personOne.getEnabled());

        PersonDTO personTwo = people.get(2);
        person = personTwo;

        assertNotNull(personTwo.getId());
        assertTrue(personTwo.getId() > 0);

        assertEquals("Bertrando", personTwo.getFirstName());
        assertEquals("Becconsall", personTwo.getLastName());
        assertEquals("35 Dryden Junction", personTwo.getAddress());
        assertEquals("Male", personTwo.getGender());
        assertFalse(personTwo.getEnabled());
    }

    private void mockPerson() {
        person.setFirstName("Linus");
        person.setLastName("Torvalds");
        person.setAddress("Helsinki - Finland");
        person.setGender("Male");
        person.setEnabled(true);
    }
}
