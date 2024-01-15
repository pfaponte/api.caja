package ec.edu.espam.api.caja.repository;

import ec.edu.espam.api.caja.domain.Account;
import ec.edu.espam.api.caja.domain.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccountId(Long accountId);

    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM MOVEMENT WHERE ACCOUNT_ID = 2 ORDER BY DATE, ID DESC")
    Optional<Movement> findByAccountOrderByDateDesc(Long accountId);

}
