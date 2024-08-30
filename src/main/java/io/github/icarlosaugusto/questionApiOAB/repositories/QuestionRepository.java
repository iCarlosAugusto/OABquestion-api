package io.github.icarlosaugusto.questionApiOAB.repositories;

import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q WHERE (:subjectId IS NULL OR q.subject.id = :subjectId) AND (:disciplineId IS NULL OR q.discipline.id = :disciplineId)")
    Page<Question> findQuestionsBySubjectAndDiscipline(
            @Param("subjectId") UUID subjectId,
            @Param("disciplineId") UUID disciplineId,
            Pageable pageable);

}
