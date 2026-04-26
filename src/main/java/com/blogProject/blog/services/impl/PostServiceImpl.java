package com.blogProject.blog.services.impl;

import com.blogProject.blog.Repositories.PostRepository;
import com.blogProject.blog.domain.CreatePostRequest;
import com.blogProject.blog.domain.PostStatus;
import com.blogProject.blog.domain.UpdatePostRequest;
import com.blogProject.blog.domain.entities.Category;
import com.blogProject.blog.domain.entities.Post;
import com.blogProject.blog.domain.entities.Tag;
import com.blogProject.blog.domain.entities.User;
import com.blogProject.blog.services.CategoryService;
import com.blogProject.blog.services.PostService;
import com.blogProject.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORD_PER_MINUTE = 200;

    @Override
    public Post getPosts(UUID id) {

        return postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("post not found with id " + id));

    }

    @Override
    public void deletePost(UUID id) {
        Post posts = getPosts(id);
        postRepository.delete(posts);
    }

    @Override
    @Transactional
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if(categoryId !=null && tagId!=null){
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag=  tagService.getTagById(tagId);

            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if(categoryId!=null){
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if(tagId!=null){
            Tag tag=  tagService.getTagById(tagId);

        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user,PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost= new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());

        newPost.setCategory(category);
        Set<UUID> tagIds = createPostRequest.getTagsId();
        List<Tag> tags =  tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("post not found with id " + id));

        existingPost.setTitle(updatePostRequest.getTitle());
        String postContent= updatePostRequest.getContent();
        existingPost.setContent(postContent);
        existingPost.setReadingTime(calReadingTime(postContent));
        existingPost.setStatus(updatePostRequest.getStatus());

        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
        if(!updatePostRequestCategoryId.equals(existingPost.getId())){

            Category newCategory = categoryService.getCategoryById(updatePostRequest.getCategoryId());
            existingPost.setCategory(newCategory);
        }

        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagsId();
        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());

        if(!existingTagIds.equals(updatePostRequestTagIds)){
            List<Tag> newTags = tagService.getTagByIds(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(newTags));

        }

        return postRepository.save(existingPost);
    }

    private Integer calReadingTime(String content){

        if(content == null || content.isEmpty()){
            return 0;
        }

        int wordCount = content.trim().split("//s+").length;

        return (int) Math.ceil((double)wordCount/WORD_PER_MINUTE);
    }
}
