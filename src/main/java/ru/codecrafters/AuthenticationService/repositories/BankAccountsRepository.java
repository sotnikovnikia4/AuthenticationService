package ru.codecrafters.AuthenticationService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.codecrafters.AuthenticationService.models.BankAccount;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankAccountsRepository extends JpaRepository<BankAccount, String> {
    @Query(value = "select * from bank_accounts where user_id=?", nativeQuery = true)
    List<BankAccount> findAllByUserId(UUID id);
}
