package io.github.icarlosaugusto.questionApiOAB.repositories;

import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Query("SELECT s FROM Subject s WHERE s.disciplines.id = :disciplineId")
    List<Subject> findSubjectsByDisciplineId(@Param("disciplineId") UUID disciplineId);
}
