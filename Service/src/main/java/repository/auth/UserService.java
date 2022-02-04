package repository.auth;

import auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.RepositoryFactory;
import users.UserImpl;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("IN loadUserByUsername ");
        Optional<UserImpl> tempUser = RepositoryFactory.getRepository().allUser().values().
                stream().filter(user -> user.getLogin().equalsIgnoreCase(s)).findFirst();
        UserImpl user = tempUser.orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });
        UserPrincipal userPrincipal = new UserPrincipal(user);
        log.info("THISPR={}", userPrincipal.getAuthorities().stream().findFirst().get().getAuthority());
        return userPrincipal;
    }
}
