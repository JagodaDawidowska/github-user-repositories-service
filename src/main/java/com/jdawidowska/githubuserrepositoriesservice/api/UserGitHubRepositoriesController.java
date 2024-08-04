package com.jdawidowska.githubuserrepositoriesservice.api;

import com.jdawidowska.githubuserrepositoriesservice.api.dto.UserRepositoriesResponse;
import com.jdawidowska.githubuserrepositoriesservice.services.GitHubApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserGitHubRepositoriesController {

    private final GitHubApiService githubApiService;

    @GetMapping("/repos")
    public List<UserRepositoriesResponse> getReposForUser(@RequestParam String username) {
        return githubApiService.getReposForUser(username);
    }
}