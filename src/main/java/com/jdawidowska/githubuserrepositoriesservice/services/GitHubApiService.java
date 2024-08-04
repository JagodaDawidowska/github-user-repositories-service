package com.jdawidowska.githubuserrepositoriesservice.services;

import com.jdawidowska.githubuserrepositoriesservice.client.GitHubApiClient;
import com.jdawidowska.githubuserrepositoriesservice.client.dto.BranchResponse;
import com.jdawidowska.githubuserrepositoriesservice.api.dto.UserRepositoriesResponse;
import com.jdawidowska.githubuserrepositoriesservice.client.dto.RepositoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubApiService {

    private final GitHubApiClient gitHubApiClient;

    public List<UserRepositoriesResponse> getReposForUser(String username) {

        List<RepositoriesResponse> reposByUser = gitHubApiClient.getReposByUser(username);
        removeForks(reposByUser);

        List<UserRepositoriesResponse> mainResponse = new ArrayList<>();

        for (RepositoriesResponse userRepo : reposByUser) {

            List<UserRepositoriesResponse.BranchInfo> branchList = new ArrayList<>();

            String branchesUrl = userRepo.getBranchesUrl();
            List<BranchResponse> retrievedBranchesInfo = gitHubApiClient.getBranchesInfo(branchesUrl);

            retrievedBranchesInfo.forEach(elementBranch -> branchList.add(new UserRepositoriesResponse.BranchInfo(
                    elementBranch.getName(),
                    elementBranch.getCommit().getSha()))
            );

            UserRepositoriesResponse res = UserRepositoriesResponse.builder()
                    .repositoryName(userRepo.getRepositoryName())
                    .ownerLogin(userRepo.getOwner().getLogin())
                    .branchInfo(branchList)
                    .build();

            mainResponse.add(res);
        }

        return mainResponse;
    }

    private static void removeForks(List<RepositoriesResponse> reposList) {
        reposList.removeIf(element -> element.getFork().equals(true));
    }
}