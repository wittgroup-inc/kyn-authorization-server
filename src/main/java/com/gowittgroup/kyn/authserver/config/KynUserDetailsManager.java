

package com.gowittgroup.kyn.authserver.config;

import com.gowittgroup.kyn.authserver.models.User;
import com.gowittgroup.kyn.authserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class KynUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {
    private final UserService userService;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUser(username);

        List<String> roles = List.of("USER");

        if (user != null) {


            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    roles.stream()
                            .map(role -> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toSet())

            );
        } else {
            throw new UsernameNotFoundException("Not found : " + username);
        }
    }
}
