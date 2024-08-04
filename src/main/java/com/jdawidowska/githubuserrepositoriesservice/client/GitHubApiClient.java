package com.jdawidowska.githubuserrepositoriesservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdawidowska.githubuserrepositoriesservice.client.dto.BranchResponse;
import com.jdawidowska.githubuserrepositoriesservice.client.dto.RepositoriesResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GitHubApiClient {

    private static final String USER_REPOS_ENDPOINT_URL = "https://api.github.com/users/%s/repos";
    private static final String BRANCHES_ENDPOINT_SUFFIX = "{/branch}";
    private final String bearerPrefix = "Bearer ";
    @Value("${global.variable}")
    private String bearerToken;
    private String completedTokenValue;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        completedTokenValue = bearerPrefix.concat(bearerToken);
    }

    public List<RepositoriesResponse> getReposByUser(String username) {
        ResponseEntity<String> response = restTemplate.exchange(
                String.format(USER_REPOS_ENDPOINT_URL, username),
                HttpMethod.GET,
                getGitHubApiEntity(),
                String.class
        );

        try {
            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new com.jdawidowska.githubuserrepositoriesservice.exception.JsonProcessingException();
        }
    }

    public List<BranchResponse> getBranchesInfo(String branchUrl) {
        ResponseEntity<String> response = restTemplate.exchange(
                buildUrl(branchUrl),
                HttpMethod.GET,
                getGitHubApiEntity(),
                String.class
        );

        try {
            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new com.jdawidowska.githubuserrepositoriesservice.exception.JsonProcessingException();
        }
    }

    private HttpEntity<String> getGitHubApiEntity() {
        HttpHeaders headers = new HttpHeaders();
        if (bearerToken != null && !bearerToken.isBlank()) {
            headers.set("Authorization", completedTokenValue);
        }
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        return new HttpEntity<>(headers);
    }

    private String buildUrl(String branchUrl) {
        return branchUrl.substring(0, branchUrl.length() - BRANCHES_ENDPOINT_SUFFIX.length());
    }
}
