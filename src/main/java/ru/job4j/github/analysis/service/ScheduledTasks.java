package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

@AllArgsConstructor
@Service
public class ScheduledTasks {

    private final RepositoryService repositoryService;
    private final GitHubService gitHubService;

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void fetchCommits() {
        var repositories = repositoryService.findAll();
        for (Repository repository : repositories) {
            var latestCommit = repositoryService.findLatestCommit(repository);
            var lastCommitSha = latestCommit != null ? latestCommit.getSha() : null;
            var commits = gitHubService.fetchNewCommits(repository.getUserName(), repository.getName(), lastCommitSha);
            if (!commits.isEmpty()) {
                for (Commit commit : commits) {
                    commit.setRepository(repository);
                    repositoryService.create(commit);
                }
            }
        }
    }
}