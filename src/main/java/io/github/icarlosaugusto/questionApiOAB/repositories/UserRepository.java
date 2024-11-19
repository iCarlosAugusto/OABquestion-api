package io.github.icarlosaugusto.questionApiOAB.repositories;

import io.github.icarlosaugusto.questionApiOAB.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
    Optional<User> findByEmailAndPassword(String email, String password);
    
    Optional<User> findByTokenGoogle(String tokenGoogle);

	Optional<User> findByTokenApple(String token);

	Optional<User> findByEmail(String email);
}
