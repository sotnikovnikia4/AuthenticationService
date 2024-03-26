package ru.codecrafters.AuthenticationService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codecrafters.AuthenticationService.models.BankAccount;

@Repository
public interface BankAccountsRepository extends JpaRepository<BankAccount, String> {
}
