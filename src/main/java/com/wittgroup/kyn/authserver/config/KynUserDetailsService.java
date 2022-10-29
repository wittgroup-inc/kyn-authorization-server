package com.wittgroup.kyn.authserver.config;

import com.wittgroup.kyn.authserver.UserDetailsMapper;
import com.wittgroup.kyn.authserver.models.User;
import com.wittgroup.kyn.authserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KynUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUser(username);
        if (user != null) return UserDetailsMapper.toUserDetails(user);
        throw new UsernameNotFoundException("Not found : " + username);
    }
}
