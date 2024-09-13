package io.github.icarlosaugusto.questionApiOAB.repositories;

import io.github.icarlosaugusto.questionApiOAB.entities.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlternativeRepository extends JpaRepository<Alternative, UUID> {
}
