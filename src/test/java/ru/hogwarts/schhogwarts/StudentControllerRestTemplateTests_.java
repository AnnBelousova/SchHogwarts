package ru.hogwarts.schhogwarts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.schhogwarts.controllers.StudentController;
import ru.hogwarts.schhogwarts.models.Faculty;
import ru.hogwarts.schhogwarts.models.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTests_{
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetStudentByID(){
        ResponseEntity<Student> newStudentEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/student", new Student(1L, "Arina", 35), Student.class);
        assertThat(newStudentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Student> studentResponseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudentEntity.getBody().getId() + "/by-id", Student.class);
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody()).isNotNull();
        assertThat(studentResponseEntity.getBody().getName()).isEqualTo("Arina");
        assertThat(studentResponseEntity.getBody().getAge()).isEqualTo(35);
    }

    @Test
    public void testCreateStudent() throws Exception{
        ResponseEntity<Student> newStudentEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/student", new Student(1L, "Arina", 35), Student.class);
        assertThat(newStudentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Student> studentResponseEntity =
                testRestTemplate.getForEntity("http://localhost:" + port +"/student"+ newStudentEntity.getBody().getId(), Student.class);
        assertThat(studentResponseEntity.equals(newStudentEntity));
    }
    @Test
    public void testDeleteById() throws Exception{
        Student newStudent = testRestTemplate.postForObject("http://localhost:" + port + "/student", new Student(1L, "Arina", 35), Student.class);
        testRestTemplate.delete("http://localhost:" + port + "/student/");
        assertThat(newStudent.equals(null));
    }
    @Test
    public void tesGetStudents() throws Exception{
        String url = "http://localhost:" + port + "/student/all";
        ParameterizedTypeReference<List<Student>> parameterizedType = new ParameterizedTypeReference<List<Student>>() {
        };
        ResponseEntity<List<Student>> students = testRestTemplate.exchange(url, HttpMethod.GET, null, parameterizedType);
        Student student1 = new Student(1L, "Arina", 35);
        Student student2 = new Student(52L, "Anna", 35);

        assertThat(students.getBody()).isNotNull();
        assertThat(students.getBody().contains(student1));
        assertThat(students.getBody()).contains(student2);
    }
}