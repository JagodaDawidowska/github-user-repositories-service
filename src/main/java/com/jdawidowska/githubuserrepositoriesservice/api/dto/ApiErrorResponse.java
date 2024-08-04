package com.jdawidowska.githubuserrepositoriesservice.api.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ApiErrorResponse {

    private String status;
    private String message;
}