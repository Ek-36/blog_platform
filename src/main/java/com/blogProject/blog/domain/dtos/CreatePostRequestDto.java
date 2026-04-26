package com.blogProject.blog.domain.dtos;

import com.blogProject.blog.domain.PostStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import java.util.UUID;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequestDto {

    @NotBlank(message = "title is required")
    @Size(min = 3,max = 200,message = "title must be between {min} and {max} char")
    private String title;

    @NotBlank(message = "content is required")
    @Size(min = 10,max = 50000,message = "content must be between {min} and {max} char")
    private String content;

    @NotNull(message = "category id is required")
    private UUID categoryId;

    @Builder.Default
    @Size(max = 10,message = "max of {max} tags allowed")
    private Set<UUID> tagsId = new HashSet<>();

    @NotNull(message = "status is required")
    private PostStatus status;
}
