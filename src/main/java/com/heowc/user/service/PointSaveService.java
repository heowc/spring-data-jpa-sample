package com.heowc.user.service;

import com.heowc.point.domain.Point;
import com.heowc.user.domain.UserPointRequest;
import com.heowc.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PointSaveService {

    @Autowired
    private UserRepository repository;

    public void save(UserPointRequest userPointRequest) {
        repository.findById(userPointRequest.getId())
                .ifPresent(u -> u.changePoint(Point.of(userPointRequest.getPoint())));
    }
}
