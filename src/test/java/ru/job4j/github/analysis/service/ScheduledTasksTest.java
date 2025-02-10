package ru.job4j.github.analysis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScheduledTasksTest {

    @Mock
    private RepositoryService repositoryService;

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private ScheduledTasks scheduledTasks;

    private Repository repository;
    private Commit commit;

    @BeforeEach
    public void setUp() {
        repository = new Repository();
        repository.setName("test-repo");
        repository.setUrl("https://github.com/user/test-repo");
        repository.setUserName("user");
        commit = new Commit();
        commit.setMessage("Initial commit");
        commit.setAuthor("user");
        commit.setRepository(repository);
    }

    @Test
    void whenFetchCommitsWithLatestCommit() {
        Commit latestCommit = new Commit();
        latestCommit.setSha("latest-sha");
        latestCommit.setRepository(repository);
        List<Commit> newCommits = Collections.singletonList(commit);
        when(repositoryService.findAll()).thenReturn(Collections.singletonList(repository));
        when(repositoryService.findLatestCommit(repository)).thenReturn(latestCommit);
        when(gitHubService.fetchNewCommits(eq("user"), eq("test-repo"), eq("latest-sha"))).thenReturn(newCommits);
        scheduledTasks.fetchCommits();
        verify(repositoryService, times(1)).findAll();
        verify(repositoryService, times(1)).findLatestCommit(repository);
        verify(gitHubService, times(1)).fetchNewCommits("user", "test-repo", "latest-sha");
        verify(repositoryService, times(1)).create(commit);
        assertThat(repository).isEqualTo(commit.getRepository());
    }

    @Test
    void whenFetchCommitsWithoutLatestCommit() {
        List<Commit> newCommits = Collections.singletonList(commit);
        when(repositoryService.findAll()).thenReturn(Collections.singletonList(repository));
        when(repositoryService.findLatestCommit(repository)).thenReturn(null);
        when(gitHubService.fetchNewCommits(eq("user"), eq("test-repo"), eq(null))).thenReturn(newCommits);
        scheduledTasks.fetchCommits();
        verify(repositoryService, times(1)).findAll();
        verify(repositoryService, times(1)).findLatestCommit(repository);
        verify(gitHubService, times(1)).fetchNewCommits("user", "test-repo", null);
        verify(repositoryService, times(1)).create(commit);
        assertThat(repository).isEqualTo(commit.getRepository());
    }
}