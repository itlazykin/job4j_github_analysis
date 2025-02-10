package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;

@Service
@AllArgsConstructor
public class GitHubService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Repository> fetchRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        ResponseEntity<List<Repository>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Repository>>() {
                }
        );
        return response.getBody();
    }

    public List<Commit> fetchCommits(String repoName) {
        String url = "https://api.github.com/repos/" + repoName + "/commits";
        ResponseEntity<List<Commit>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Commit>>() {

                }
        );
        return response.getBody();
    }

    public List<Commit> fetchNewCommits(String owner, String repoName, String sha) {
        String url = String.format("https://api.github.com/repos/%s/%s/commits?sha=%s", owner, repoName, sha);
        ResponseEntity<List<Commit>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Commit>>() {
                }
        );
        return response.getBody();
    }
}