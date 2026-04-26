package com.blogProject.blog.services;

import com.blogProject.blog.domain.entities.User;

import java.util.UUID;

public interface UserService
{
    User getUserById(UUID id);
}
