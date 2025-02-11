package ru.job4j.github.analysis.service;

import org.junit.jupiter.api.Test;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.CommitRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommitServiceTest {

    private final CommitRepository commitRepository = mock(CommitRepository.class);

    private final GitHubService gitHubService = mock(GitHubService.class);

    private final CommitService commitService = new CommitService(commitRepository);

    @Test
    void testCreateCommit() {
        Commit commit = new Commit();
        commitService.create(commit);
        verify(commitRepository, times(1)).save(commit);
    }

    @Test
    void testFindLastCommitByRepository() {
        Repository repository = new Repository();
        repository.setId(1L);
        Commit expectedCommit = new Commit();
        when(commitRepository.findTopByRepositoryOrderByDateDesc(repository.getId())).thenReturn(expectedCommit);
        Commit result = commitService.findLastCommitByRepository(repository);
        assertEquals(expectedCommit, result);
        verify(commitRepository, times(1)).findTopByRepositoryOrderByDateDesc(repository.getId());
    }
}