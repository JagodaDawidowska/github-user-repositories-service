package com.jdawidowska.githubuserrepositoriesservice.api.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserRepositoriesResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<BranchInfo> branchInfo;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class BranchInfo {

        private String branchName;
        private String commitSha;
    }
}