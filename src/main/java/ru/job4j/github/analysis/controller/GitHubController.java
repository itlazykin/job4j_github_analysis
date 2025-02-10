package ru.job4j.github.analysis.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.github.analysis.dto.RepositoryCommits;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.service.RepositoryService;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GitHubController {

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/repositories")
    public List<Repository> getAllRepositories() {
        return repositoryService.findAll();
    }

    @GetMapping("/commits/{name}")
    public List<RepositoryCommits> getCommits(@PathVariable(value = "name") String name) {
        return repositoryService.getCommitsByUserName(name);
    }

    @PostMapping("/repository")
    public ResponseEntity<Void> create(@RequestBody Repository repository) {
        this.repositoryService.create(repository);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/gitHub/{userName}")
    public ResponseEntity<Void> create(@PathVariable(value = "userName") String userName) {
        this.repositoryService.createForUser(userName);
        return ResponseEntity.noContent().build();
    }
}