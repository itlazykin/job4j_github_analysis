package ru.job4j.github.analysis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubService gitHubService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(GitHubServiceTest.class);
    }

    @Test
    void whenFetchRepositories() {
        var username = "user";
        var url = "https://api.github.com/users/" + username + "/repos";
        var repository = new Repository();
        repository.setName("test");
        List<Repository> mockResponse = Collections.singletonList(repository);
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
        var repositories = gitHubService.fetchRepositories(username);
        assertThat(repositories).hasSize(1);
        assertThat(repositories.get(0).getName()).isEqualTo("test");
    }

    @Test
    void whenFetchCommits() {
        var owner = "user";
        var repoName = "test";
        var sha = "abcdef123456";
        var url = String.format("https://api.github.com/repos/%s/%s/commits?sha=%s", owner, repoName, sha);
        var commit = new Commit();
        commit.setMessage("Initial commit");
        List<Commit> mockResponse = Collections.singletonList(commit);
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
        var commits = gitHubService.fetchNewCommits(owner, repoName, sha);
        assertThat(commits).hasSize(1);
        assertThat(commits.get(0).getMessage()).isEqualTo("Initial commit");
    }

    @Test
    void whenFetchRepositoriesThenGetEmptyList() {
        var username = "user";
        var url = "https://api.github.com/users/" + username + "/repos";
        List<Repository> mockResponse = Collections.emptyList();
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
        var repositories = gitHubService.fetchRepositories(username);
        assertThat(repositories).isEmpty();
    }

    @Test
    void whenFetchCommitsThenGetEmptyList() {
        var owner = "user";
        var repoName = "test";
        var sha = "abcdef123456";
        var url = String.format("https://api.github.com/repos/%s/%s/commits?sha=%s", owner, repoName, sha);
        List<Commit> mockResponse = Collections.emptyList();
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
        var commits = gitHubService.fetchNewCommits(owner, repoName, sha);
        assertThat(commits).isEmpty();
    }
}