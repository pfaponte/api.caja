package ec.edu.espam.api.caja.service.auth;

import ec.edu.espam.api.caja.domain.auth.UserInfo;
import ec.edu.espam.api.caja.exceptions.PreconditionFailedException;
import ec.edu.espam.api.caja.repository.auth.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByName(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public UserInfo addUser(UserInfo userInfo) {
        validate(userInfo);

        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }

    private void validate(UserInfo userInfo) {
        if (repository.findByName(userInfo.getName()).isPresent()) {
            throw new PreconditionFailedException("User already exists");
        }
    }
}
