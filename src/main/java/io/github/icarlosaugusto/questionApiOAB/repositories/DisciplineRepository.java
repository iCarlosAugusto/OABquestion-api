package io.github.icarlosaugusto.questionApiOAB.repositories;

import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
