package ru.hogwarts.schhogwarts;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.schhogwarts.controllers.StudentController;

import ru.hogwarts.schhogwarts.models.Student;
import ru.hogwarts.schhogwarts.repositories.StudentRepository;
import ru.hogwarts.schhogwarts.services.impl.StudentServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
public class SchHogwartsApplicationTestsStudents {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private StudentServiceImpl studentService;
    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void getStudentById() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "Leo", 25)));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1/by-id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Leo"));
    }

    @Test
    public void createStudent() throws Exception {
        JSONObject studentObj = new JSONObject();
        studentObj.put("id", 1L);
        studentObj.put("name", "Anna");
        studentObj.put("age", 30);
        Student student = new Student(1L, "Anna", 30);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.age").value(30));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1/by-id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    public void deleteStudent() throws Exception {
        JSONObject studentObj = new JSONObject();
        studentObj.put("name", "Anna");
        studentObj.put("age", 30);
        Student student = new Student(1L, "Anna", 30);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.deleteById(1L)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1/by-id"))
                .andExpect(status().isOk())
                .andDo(print());;
    }
    @Test
    public void getListOfStudents() throws Exception{
        Student student1 = new Student(1L, "Anna", 30);
        Student student2 = new Student(2L, "Ivan", 20);
        Student student3 = new Student(3L, "Lena", 40);
        Student student4 = new Student(4L, "Ruslan", 35);
        Student student5 = new Student(5L, "Rita", 45);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        when(studentRepository.getStudentsLimitFiveDESC()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.get("/student/five"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].name").value("Anna"));
    }
    @Test
    public void getAVGAge() throws Exception{
        Student student1 = new Student(1L, "Anna", 30);
        Student student2 = new Student(2L, "Ivan", 20);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        when(studentRepository.getAVGStudentAge()).thenReturn(25);
        mockMvc.perform(MockMvcRequestBuilders.get("/student/avg-age"))
                .andExpect(jsonPath("$").value(25))
                .andDo(print());
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTests{
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