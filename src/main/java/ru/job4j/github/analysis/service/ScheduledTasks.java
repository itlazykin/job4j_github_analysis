package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;

@AllArgsConstructor
@Service
public class ScheduledTasks {

    private final RepositoryService repositoryService;
    private final CommitService commitService;
    private final GitHubService gitHubService;

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void fetchCommits() {
        List<Repository> repositories = repositoryService.getAllRepositories();
        for (Repository repository : repositories) {
            Commit lastCommit = commitService.findLastCommitByRepository(repository);
            String sha = lastCommit != null ? lastCommit.getSha() : null;
            List<Commit> commits = gitHubService.fetchCommits(
                    repository.getUsername(),
                    repository.getName(),
                    sha
            );
            if (!commits.isEmpty()) {
                for (Commit commit : commits) {
                    commitService.create(commit);
                }
            }
        }
    }
}
