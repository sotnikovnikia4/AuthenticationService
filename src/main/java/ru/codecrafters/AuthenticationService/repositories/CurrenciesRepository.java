package ru.codecrafters.AuthenticationService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codecrafters.AuthenticationService.models.Currency;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currency, String> {
}
