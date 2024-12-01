package io.github.icarlosaugusto.questionApiOAB.repositories;

import io.github.icarlosaugusto.questionApiOAB.entities.RepliedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepliedQuestionRepository extends JpaRepository<RepliedQuestion, UUID> {
}
