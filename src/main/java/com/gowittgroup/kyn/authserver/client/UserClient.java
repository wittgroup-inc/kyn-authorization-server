
package com.gowittgroup.kyn.authserver.client;

import com.gowittgroup.kyn.authserver.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient("profile-management")
public interface UserClient {
    @GetMapping("/api/profiles/loadUser/{q}")
    User loadUser(@PathVariable String q);

    @PostMapping("/api/profiles/createUser")
    UUID signUp(@RequestBody User user);
}
