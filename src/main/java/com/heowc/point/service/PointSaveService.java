package com.heowc.point.service;

import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import com.heowc.point.domain.PointHistoryRepository;
import com.heowc.user.domain.UserPointRequest;
import com.heowc.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PointSaveService {

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    public void save(UserPointRequest userPointRequest) {
        userRepository.findById(userPointRequest.getId())
                .ifPresent(user -> {
                    PointHistory pointHistory = new PointHistory(Point.of(userPointRequest.getPoint()));
                    Point modifiedTotalPoint = Point.of(user.getTotalPoint().getValue() + pointHistory.getPoint().getValue());
                    user.setTotalPoint(modifiedTotalPoint);
                    pointHistory.setUser(user);
                    pointHistoryRepository.save(pointHistory);
                });
    }
}