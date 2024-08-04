package com.jdawidowska.githubuserrepositoriesservice.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RepositoriesResponse {

    @JsonProperty("name")
    private String repositoryName;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("fork")
    private Boolean fork;
    @JsonProperty("branches_url")
    private String branchesUrl;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class Owner {
        @JsonProperty("login")
        private String login;
    }
}