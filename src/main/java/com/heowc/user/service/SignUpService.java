package com.heowc.user.service;

import com.heowc.user.domain.User;
import com.heowc.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class SignUpService {

    @Autowired
    private UserRepository repository;

    public void join(User user) {
        if (repository.existsById(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("'%s' is already exist", user.getId()));
        }

        repository.save(user);
    }
}
