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
import ru.hogwarts.schhogwarts.controllers.FacultyController;
import ru.hogwarts.schhogwarts.models.Faculty;
import ru.hogwarts.schhogwarts.repositories.FacultyRepository;
import ru.hogwarts.schhogwarts.services.impl.FacultyServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerMockMVCTests {
  @Autowired
  private MockMvc mockMvc;
  @SpyBean
  private FacultyServiceImpl facultyService;
  @MockBean
  private FacultyRepository facultyRepository;
  @Test
  public void testGetFacultyById() throws Exception{
    when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty(1,"one", "red")));
    mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("one"))
            .andExpect(jsonPath("$.color").value("red"));
    verify(facultyRepository, times(1)).findById(1L);
    verify(facultyService, times(1)).findFaculty(1L);
  }

  @Test
  public void createFaculty() throws Exception{
    JSONObject facultyObj = new JSONObject();
    facultyObj.put( "name","one");
    facultyObj.put( "color","yellow");
    Faculty faculty = new Faculty(1L, "one", "yellow");

    when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
    when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
    mockMvc.perform(MockMvcRequestBuilders
                    .post("/faculty")
                    .content(facultyObj.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("one"))
            .andExpect(jsonPath("$.color").value("yellow"));
    mockMvc.perform(MockMvcRequestBuilders
                    .get("/faculty/1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("one"))
            .andExpect(jsonPath("$.color").value("yellow"))
            .andDo(print());

  }
  @Test
  public void deleteFaculty() throws Exception{
    JSONObject facultyObj = new JSONObject();
    facultyObj.put( "name","one");
    facultyObj.put( "color","yellow");
    Faculty faculty = new Faculty(1L, "one", "yellow");
    when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
    when(facultyRepository.deleteById(1L)).thenReturn(Optional.of(faculty));
    mockMvc.perform(MockMvcRequestBuilders
                    .delete("/faculty/1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
  }

  @Test
  public void getFacultyByNameAndId() throws Exception{
    Faculty faculty = new Faculty(1L, "one", "blue");
    when(facultyRepository.findFacultyByNameAndColor("one","blue")).thenReturn(Optional.of(faculty));
    mockMvc.perform(MockMvcRequestBuilders
                    .get("/faculty/find?name=one&color=blue"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("one"))
            .andExpect(jsonPath("$.color").value("blue"))
            .andDo(print());
  }

  @Test
  public void getListOfFaculties() throws Exception{
    Faculty faculty = new Faculty(1L, "one", "blue");
    Faculty faculty1 = new Faculty(2L, "two", "red");
    List<Faculty> faculties = new ArrayList<>();
    faculties.add(faculty);
    faculties.add(faculty1);
    when(facultyRepository.findAll()).thenReturn(faculties);
    mockMvc.perform(MockMvcRequestBuilders
                    .get("/faculty/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].name").value("one"))
            .andDo(print());
  }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTemplatesTests {
  @LocalServerPort
  private int port;
  @Autowired
  private FacultyController facultyController;
  @Autowired
  private TestRestTemplate testRestTemplate;
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
