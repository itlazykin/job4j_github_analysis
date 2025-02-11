package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.dto.RepositoryCommits;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.CommitRepository;
import ru.job4j.github.analysis.repository.RepositoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepositoryService {

    private RepositoryRepository repositoryRepository;
    private GitHubService gitHubService;

    @Async
    public void create(Repository repository) {
        repositoryRepository.save(repository);
    }

    public List<Repository> getAllRepositories() {
        return repositoryRepository.findAll();
    }

    public List<RepositoryCommits> getAllCommits(String name) {
        List<Repository> repository = gitHubService.fetchRepositories(name);
        List<Commit> commits = gitHubService.fetchCommits(name);

        return repository.stream().map(repos -> new RepositoryCommits(
                        repos.getId(),
                        repos.getName(),
                        repos.getUrl(),
                        repos.getUsername(),
                        commits
                )
        ).collect(Collectors.toList());
    }
}
