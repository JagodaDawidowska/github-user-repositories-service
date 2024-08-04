package com.jdawidowska.githubuserrepositoriesservice.services;

import com.jdawidowska.githubuserrepositoriesservice.api.dto.UserRepositoriesResponse;
import com.jdawidowska.githubuserrepositoriesservice.client.GitHubApiClient;
import com.jdawidowska.githubuserrepositoriesservice.client.dto.BranchResponse;
import com.jdawidowska.githubuserrepositoriesservice.client.dto.RepositoriesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class GitHubApiServiceTest {

    @InjectMocks
    private GitHubApiService cut;
    @Mock
    private GitHubApiClient client;

    @Test
    void getReposForUser_should() {
        //given
        List<RepositoriesResponse> repositoriesResponseList = new ArrayList<>();
        repositoriesResponseList.add(RepositoriesResponse.builder()
                .repositoryName("repo1")
                .owner(RepositoriesResponse.Owner.builder()
                        .login("ownerLogin1")
                        .build())
                .fork(true)
                .branchesUrl("branchUrl1")
                .build());
        repositoriesResponseList.add(RepositoriesResponse.builder()
                .repositoryName("repo2")
                .owner(RepositoriesResponse.Owner.builder()
                        .login("ownerLogin2")
                        .build())
                .fork(false)
                .branchesUrl("branchUrl2")
                .build());

        Mockito.when(client.getReposByUser(eq("user")))
                .thenReturn(repositoriesResponseList);

        Mockito.when(client.getBranchesInfo(eq("branchUrl2")))
                .thenReturn(List.of(
                        BranchResponse.builder()
                                .name("repo2branch1")
                                .commit(BranchResponse.Commit.builder().sha("repo2branch1sha1").build())
                                .build(),
                        BranchResponse.builder()
                                .name("repo2branch2")
                                .commit(BranchResponse.Commit.builder().sha("repo2branch2sha1").build())
                                .build()
                ));

        //when
        List<UserRepositoriesResponse> userRepos = cut.getReposForUser("user");

        //then
        assertEquals(1, userRepos.size());
        assertEquals("repo2", userRepos.get(0).getRepositoryName());
        assertEquals("ownerLogin2", userRepos.get(0).getOwnerLogin());
        assertEquals(2, userRepos.get(0).getBranchInfo().size());
        assertEquals("repo2branch1", userRepos.get(0).getBranchInfo().get(0).getBranchName());
        assertEquals("repo2branch1sha1", userRepos.get(0).getBranchInfo().get(0).getCommitSha());
        assertEquals("repo2branch2", userRepos.get(0).getBranchInfo().get(1).getBranchName());
        assertEquals("repo2branch2sha1", userRepos.get(0).getBranchInfo().get(1).getCommitSha());
    }
}