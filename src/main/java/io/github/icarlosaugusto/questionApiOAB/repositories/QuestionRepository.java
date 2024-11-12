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


    //Funcionando
    @Query("SELECT q FROM Question q WHERE "
        + "(COALESCE(:subjectIds, NULL) IS NULL OR q.subject.id IN (:subjectIds)) "
        + "AND (COALESCE(:disciplineIds, NULL) IS NULL OR q.discipline.id IN (:disciplineIds)) "
        + "AND ("
        + "  (:myQuestionType = 'resolved' AND q.id IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId)) "
        + "  OR (:myQuestionType = 'notResolved' AND q.id NOT IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId)) "
        + "  OR (:myQuestionType = 'all') "
        + "  OR (:myQuestionType = 'resolvedCorrect' AND q.id IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId AND rq.repliedCorrect = true)) "
        + "  OR (:myQuestionType = 'resolvedWrong' AND q.id IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId AND rq.repliedCorrect = false)) "
        + ")"
    )
//    @Query("SELECT q FROM Question q WHERE "
//            + "(COALESCE(:subjectIds, NULL) IS NULL OR q.subject.id IN (:subjectIds)) "
//            + "AND (COALESCE(:disciplineIds, NULL) IS NULL OR q.discipline.id IN (:disciplineIds)) "
//            + "AND ("
//            + "  (:myQuestionType = 'resolved' AND ( :userId IS NULL OR q.id IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId))) "
//            + "  OR (:myQuestionType = 'notResolved' AND ( :userId IS NULL OR q.id NOT IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId))) "
//            + "  OR (:myQuestionType = 'all') "
//            + "  OR (:myQuestionType = 'resolvedCorrect' AND ( :userId IS NULL OR q.id IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId AND rq.repliedCorrect = true))) "
//            + "  OR (:myQuestionType = 'resolvedWrong' AND ( :userId IS NULL OR q.id IN (SELECT rq.question.id FROM RepliedQuestion rq WHERE rq.user.id = :userId AND rq.repliedCorrect = false))) "
//            + ")"
//    )
    Page<Question> findQuestionsBySubjectsAndDisciplines(
            @Param("disciplineIds") List<String> disciplineIds,
            @Param("subjectIds") List<String> subjectIds,
            @Param("userId") UUID userId,
            @Param("myQuestionType") String myQuestionType,
            Pageable pageable
    );
}
