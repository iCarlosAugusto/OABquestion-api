package io.github.icarlosaugusto.questionApiOAB.repositories;

import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q WHERE (:subjectIds IS NULL OR q.subject.id IN :subjectIds) AND (:disciplineIds IS NULL OR q.discipline.id IN :disciplineIds)")
    Page<Question> findQuestionsBySubjectsAndDisciplines(
            @Param("disciplineIds") List<String> disciplineIds,
            @Param("subjectIds") List<String> subjectIds,
            Pageable pageable
    );


}
