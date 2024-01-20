package ec.edu.espam.api.caja.repository.auth;

import ec.edu.espam.api.caja.domain.auth.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);
}
