package com.blogProject.blog.Controller;

import com.blogProject.blog.domain.CreatePostRequest;
import com.blogProject.blog.domain.UpdatePostRequest;
import com.blogProject.blog.domain.dtos.CreatePostRequestDto;
import com.blogProject.blog.domain.dtos.PostDto;
import com.blogProject.blog.domain.dtos.UpdatePostRequestDto;
import com.blogProject.blog.domain.entities.Post;
import com.blogProject.blog.domain.entities.User;
import com.blogProject.blog.mapper.PostMapper;
import com.blogProject.blog.services.PostService;
import com.blogProject.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false)UUID tagId){

        List<Post> posts = postService.getAllPosts(categoryId,tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId){
            User logedInUser = userService.getUserById(userId);

            List<Post> draftPosts = postService.getDraftPosts(logedInUser);
            List<PostDto> postDtos =  draftPosts.stream()
                    .map(postMapper::toDto).toList();

            return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId
            ){
        User logedInUser = userService.getUserById(userId);
        CreatePostRequest  createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(logedInUser, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);

        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
            ){
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post post = postService.updatePost(id, updatePostRequest);
        PostDto dto = postMapper.toDto(post);

        return ResponseEntity.ok(dto);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPosts(
            @PathVariable UUID id
    ){
        Post post = postService.getPosts(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable UUID id
    ){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
