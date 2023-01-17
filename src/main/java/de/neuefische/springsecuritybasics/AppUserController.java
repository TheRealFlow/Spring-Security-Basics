package de.neuefische.springsecuritybasics;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/app-user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping
    public AppUser create (@RequestBody AppUser appUser) {
        return appUserService.create(appUser);
    }

    @PostMapping("/login")
    public Optional<AppUser> login() {
        return me();
    }

    @PostMapping("/me")
    public Optional<AppUser> me() {
        return appUserService.findByUsernameWithoutPassword(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    @PostMapping("/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
