
/*
package com.wittgroup.kyn.authserver.config;

import com.wittgroup.kyn.authserver.UserDetailsMapper;
import com.wittgroup.kyn.authserver.models.User;
import com.wittgroup.kyn.authserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
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
        if (user != null) return UserDetailsMapper.toUserDetails(user);
        throw new UsernameNotFoundException("Not found : " + username);
    }
}
*/