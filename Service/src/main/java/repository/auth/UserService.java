package repository.auth;

import auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.RepositoryFactory;
import users.UserImpl;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserImpl> tempUser = RepositoryFactory.getRepository().allUser().values().
                stream().filter(user -> user.getName().equalsIgnoreCase(s)).findFirst();
        UserImpl user = tempUser.orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });

        return new UserPrincipal(user);
    }
}
