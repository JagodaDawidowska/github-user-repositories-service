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
public class BranchResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("commit")
    private Commit commit;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class Commit {
        @JsonProperty("sha")
        private String sha;
    }
}