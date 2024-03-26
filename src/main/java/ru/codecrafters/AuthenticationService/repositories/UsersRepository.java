package ru.codecrafters.AuthenticationService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codecrafters.AuthenticationService.models.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}
