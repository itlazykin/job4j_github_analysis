package ru.job4j.github.analysis.service;

import org.junit.jupiter.api.Test;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.RepositoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RepositoryServiceTest {

    private final RepositoryRepository repositoryRepository = mock(RepositoryRepository.class);

    private final GitHubService gitHubService = mock(GitHubService.class);

    private final RepositoryService repositoryService = new RepositoryService(repositoryRepository, gitHubService);

    @Test
    void testCreateRepository() {
        Repository repository = new Repository();
        repositoryService.create(repository);
        verify(repositoryRepository, times(1)).save(repository);
    }

    @Test
    void testGetAllRepositories() {
        List<Repository> expectedRepositories = List.of(new Repository());
        when(repositoryRepository.findAll()).thenReturn(expectedRepositories);

        List<Repository> result = repositoryService.getAllRepositories();

        assertEquals(expectedRepositories, result);
        verify(repositoryRepository, times(1)).findAll();
    }

}