package ru.hogwarts.schhogwarts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.hibernate.mapping.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.schhogwarts.controllers.FacultyController;
import ru.hogwarts.schhogwarts.models.Faculty;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTemplatesTests {
  @LocalServerPort
  private int port;
  @Autowired
  private FacultyController facultyController;
  @Autowired
  private TestRestTemplate testRestTemplate;
  @Test
  void contextLoads(){

  }
  @Test
  public void testGetFacultyByID(){
    ResponseEntity<Faculty> newFacultyEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", new Faculty("one", "blue"), Faculty.class);
    assertThat(newFacultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    ResponseEntity<Faculty> facultyResponseEntity =
            testRestTemplate.getForEntity("http://localhost:" + port +"/faculty/"+ newFacultyEntity.getBody().getId(), Faculty.class);

    assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(facultyResponseEntity.getBody()).isNotNull();
    assertThat(facultyResponseEntity.getBody().getName()).isEqualTo("one");
    assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo("blue");
  }
  @Test
  public void testFindById() throws Exception{
    ResponseEntity<Faculty> newFacultyEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", new Faculty(1,"one", "blue"), Faculty.class);
    assertThat(newFacultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.getForEntity("http://localhost:" + port +"/faculty/1", Faculty.class);
    assertThat(facultyResponseEntity.getBody().getName()).isEqualTo("one");
  }

  @Test
  public void testDeleteById() throws Exception{
    Faculty newFacultyEntity = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", new Faculty(1,"one", "blue"), Faculty.class);
    testRestTemplate.delete("http://localhost:" + port + "/faculty/1");
    assertThat(newFacultyEntity.equals(null));
  }
  @Test
  public void testCreateFaculty() throws Exception{
    ResponseEntity<Faculty> newFacultyEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", new Faculty("one", "blue"), Faculty.class);
    assertThat(newFacultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    ResponseEntity<Faculty> facultyResponseEntity =
            testRestTemplate.getForEntity("http://localhost:" + port +"/faculty/"+ newFacultyEntity.getBody().getId(), Faculty.class);
    assertThat(facultyResponseEntity.equals(newFacultyEntity));
  }
  @Test
  public void testGetFaculties() throws Exception{
//    Faculty newFacultyEntity1 = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", new Faculty(1,"one", "blue"), Faculty.class);
//    Faculty newFacultyEntity2 = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", new Faculty(2,"two", "red"), Faculty.class);
//    List<Faculty> list = new ArrayList<>();
//    list.add(newFacultyEntity1);
//    list.add(newFacultyEntity2);
//    assertThat(list.size() == 2);
//
    String url = "http://localhost:" + port + "/faculty/all";
    ParameterizedTypeReference<List<Faculty>> paramType = new ParameterizedTypeReference<List<Faculty>>() {
    };
    ResponseEntity<List<Faculty>> faculties = testRestTemplate.exchange(url, HttpMethod.GET, null, paramType);
    Faculty facultyOne = new Faculty(1L, "one", "red");

    Faculty facultyTwo = new Faculty(2L, "two", "blue");

    assertThat(faculties.getBody()).isNotNull();
    assertThat(faculties.getBody().contains(facultyTwo));
    assertThat(faculties.getBody().contains(facultyOne));
  }
}
