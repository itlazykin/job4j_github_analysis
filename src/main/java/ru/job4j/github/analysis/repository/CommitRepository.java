package ru.job4j.github.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.github.analysis.model.Commit;

public interface CommitRepository extends JpaRepository<Commit, Long> {

    @Query("""
            SELECT c FROM Commit c
            WHERE c.repository.id = :repositoryId
            ORDER BY c.date DESC
            """
    )
    Commit findTopByRepositoryOrderByDateDesc(@Param("repositoryId") Long repositoryId);
}
