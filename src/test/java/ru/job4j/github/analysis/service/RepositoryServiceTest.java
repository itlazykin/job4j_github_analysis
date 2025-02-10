package ru.job4j.github.analysis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.CommitRepository;
import ru.job4j.github.analysis.repository.RepositoryRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceTest {

    @Mock
    private RepositoryRepository repositoryRepository;

    @Mock
    private CommitRepository commitRepository;

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private RepositoryService repositoryService;

    private Repository repository;
    private Commit commit;

    @BeforeEach
    public void setUp() {
        repository = new Repository();
        repository.setName("test");
        repository.setUrl("https://github.com/user/test");
        commit = new Commit();
        commit.setMessage("Initial commit");
        commit.setAuthor("user");
        commit.setRepository(repository);
    }

    @Test
    void whenFindAll() {
        when(repositoryRepository.findAll()).thenReturn(Collections.singletonList(repository));
        var repositories = repositoryService.findAll();
        assertThat(repositories).hasSize(1);
        assertThat(repositories.get(0).getName()).isEqualTo("test");
        verify(repositoryRepository, times(1)).findAll();
    }

    @Test
    void whenFindByName() {
        when(repositoryRepository.findByName("test")).thenReturn(Optional.of(repository));
        var foundRepository = repositoryService.findByName("test");
        assertThat(foundRepository.get().getName()).isEqualTo("test");
        verify(repositoryRepository, times(1)).findByName("test");
    }

    @Test
    void whenFindCommitsByRepositoryName() {
        when(commitRepository.findAllByRepositoryName("test")).thenReturn(Collections.singletonList(commit));
        var commits = repositoryService.findCommitsByRepositoryName("test");
        assertThat(commits).hasSize(1);
        assertThat(commits.get(0).getMessage()).isEqualTo("Initial commit");
        verify(commitRepository, times(1)).findAllByRepositoryName("test");
    }

    @Test
    void whenFindLatestCommit() {
        when(commitRepository.findTopByRepositoryOrderByDateDesc(repository)).thenReturn(commit);
        var latestCommit = repositoryService.findLatestCommit(repository);
        assertThat(latestCommit.getMessage()).isEqualTo("Initial commit");
        verify(commitRepository, times(1)).findTopByRepositoryOrderByDateDesc(repository);
    }

    @Test
    void whenCreateCommit() {
        repositoryService.create(commit);
        verify(commitRepository, times(1)).save(commit);
    }

    @Test
    void whenCreateRepositoriesForUser() {
        when(gitHubService.fetchRepositories("user")).thenReturn(Collections.singletonList(repository));
        repositoryService.createForUser("user");
        verify(gitHubService, times(1)).fetchRepositories("user");
        verify(repositoryRepository, times(1)).save(repository);
    }
}