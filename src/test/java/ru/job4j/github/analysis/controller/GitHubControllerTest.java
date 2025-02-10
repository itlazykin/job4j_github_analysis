package ru.job4j.github.analysis.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.service.RepositoryService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(GitHubController.class)
@ExtendWith(MockitoExtension.class)
class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryService repositoryService;

    private Repository repository;

    private Commit commit;

    @BeforeEach
    public void setUp() {
        repository = new Repository();
        repository.setName("test-repo");
        repository.setUrl("https://github.com/user/test-repo");
        commit = new Commit();
        commit.setMessage("Initial commit");
        commit.setAuthor("user");
        commit.setSha("abcd1234");
    }

    @Test
    void whenGetAllRepositories() throws Exception {
        when(repositoryService.findAll()).thenReturn(Collections.singletonList(repository));
        mockMvc.perform(get("/api/repositories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test-repo"))
                .andExpect(jsonPath("$[0].url").value("https://github.com/user/test-repo"));
        verify(repositoryService, times(1)).findAll();
    }

    @Test
    void whenCreateForUser() throws Exception {
        doNothing().when(repositoryService).createForUser("user");
        mockMvc.perform(post("/api/gitHub/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(repositoryService, times(1)).createForUser("user");
    }
}