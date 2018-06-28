package com.heowc.user.service;

import com.heowc.user.domain.User;
import com.heowc.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PasswordClearService {

    @Autowired
    private UserRepository repository;

    public void clear(String id) {
        repository.findById(id).ifPresent(User::clearPassword);
    }
}

