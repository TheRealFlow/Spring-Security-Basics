package de.neuefische.springsecuritybasics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app-user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping
    public AppUser post (@RequestBody AppUser appUser) {
        return appUserService.create(appUser);
    }
}
