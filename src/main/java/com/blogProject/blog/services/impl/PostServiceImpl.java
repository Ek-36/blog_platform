package com.blogProject.blog.services.impl;

import com.blogProject.blog.Repositories.PostRepository;
import com.blogProject.blog.domain.entities.Post;
import com.blogProject.blog.services.CategoryService;
import com.blogProject.blog.services.PostService;
import com.blogProject.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        return List.of();
    }
}
