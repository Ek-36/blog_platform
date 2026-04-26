package com.blogProject.blog.services;

import com.blogProject.blog.domain.CreatePostRequest;
import com.blogProject.blog.domain.UpdatePostRequest;
import com.blogProject.blog.domain.entities.Post;
import com.blogProject.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Post getPosts(UUID id);
    void deletePost(UUID id);
    List<Post> getAllPosts(UUID categoryId , UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
}
