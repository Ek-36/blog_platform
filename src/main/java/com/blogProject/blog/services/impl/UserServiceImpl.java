package com.blogProject.blog.services.impl;

import com.blogProject.blog.Repositories.UserRepository;
import com.blogProject.blog.domain.entities.User;
import com.blogProject.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("user not found with id :" + id));
    }
}
