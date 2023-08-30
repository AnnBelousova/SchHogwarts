package ru.hogwarts.schhogwarts;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.schhogwarts.controllers.StudentController;
import ru.hogwarts.schhogwarts.models.Student;
import ru.hogwarts.schhogwarts.repositories.StudentRepository;
import ru.hogwarts.schhogwarts.services.impl.StudentServiceImpl;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
public class SchHogwartsApplicationTestsStudents {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private StudentServiceImpl studentService;
    @MockBean
    private StudentRepository studentRepository;
    @Test
    public void getStudentById() throws Exception{
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L,"Leo", 25)));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1/by-id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Leo"));
    }
//    @Test
//    public void createStudent() throws Exception{
//        JSONObject studentObj = new JSONObject();
//        studentObj.put("id", 1);
//        studentObj.put("name", "Anna");
//        studentObj.put("age", 30);
//        Student student = new Student(1L,"Anna", 30);
//        when(studentRepository.save(any(Student.class))).thenReturn()
//    }
}
