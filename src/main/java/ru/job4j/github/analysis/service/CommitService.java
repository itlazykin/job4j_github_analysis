package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.CommitRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommitService {

    private CommitRepository commitRepository;

    @Async
    public void create(Commit commit) {
        commitRepository.save(commit);
    }

    public Commit findLastCommitByRepository(Repository repository) {
        return commitRepository.findTopByRepositoryOrderByDateDesc(repository.getId());
    }
}
