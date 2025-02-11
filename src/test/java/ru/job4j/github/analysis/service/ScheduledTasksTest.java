package ru.job4j.github.analysis.service;

import org.junit.jupiter.api.Test;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;

import static org.mockito.Mockito.*;

class ScheduledTasksTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class);

    private final CommitService commitService = mock(CommitService.class);

    private final GitHubService gitHubService = mock(GitHubService.class);

    private final ScheduledTasks scheduledTasks = new ScheduledTasks(repositoryService, commitService, gitHubService);

    @Test
    void testFetchCommits() {
        Repository repository = new Repository();
        repository.setId(1L);
        repository.setUsername("testuser");
        repository.setName("testrepo");
        List<Repository> repositories = List.of(repository);
        Commit lastCommit = new Commit();
        lastCommit.setSha("123");
        List<Commit> newCommits = List.of(new Commit());
        when(repositoryService.getAllRepositories()).thenReturn(repositories);
        when(commitService.findLastCommitByRepository(repository)).thenReturn(lastCommit);
        when(gitHubService.fetchCommits("testuser", "testrepo", "123")).thenReturn(newCommits);
        scheduledTasks.fetchCommits();
        verify(repositoryService, times(1)).getAllRepositories();
        verify(commitService, times(1)).findLastCommitByRepository(repository);
        verify(gitHubService, times(1)).fetchCommits("testuser", "testrepo", "123");
        verify(commitService, times(newCommits.size())).create(any(Commit.class));
    }
}