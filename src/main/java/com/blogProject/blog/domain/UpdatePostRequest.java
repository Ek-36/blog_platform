package com.blogProject.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UpdatePostRequest {
    private String title;

    private String content;

    private UUID categoryId;

    @Builder.Default
    private Set<UUID> tagsId = new HashSet<>();

    private PostStatus status;
}
