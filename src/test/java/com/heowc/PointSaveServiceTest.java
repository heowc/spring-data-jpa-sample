package com.heowc;

import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import com.heowc.point.domain.PointHistoryRepository;
import com.heowc.user.domain.User;
import com.heowc.user.domain.UserPointRequest;
import com.heowc.user.domain.UserRepository;
import com.heowc.point.service.PointSaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PointSaveServiceTest {

    @Autowired
    private PointSaveService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    @Test
    public void changeSuccess() {
        // given
        User user = new User("heowc1992", null, null, null);
        User savedUser = userRepository.save(user);

        List<UserPointRequest> userPointRequestList = Arrays.asList(
                new UserPointRequest(savedUser.getId(), 100L),
                new UserPointRequest(savedUser.getId(), 300L)
        );
        userPointRequestList.forEach(userPointRequest -> service.save(userPointRequest));
        userRepository.flush();

        // when
        User changedUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertThat(changedUser).isNotNull();
        assertThat(changedUser.getTotalPoint().getValue()).isEqualTo(userPointRequestList.stream().mapToLong(UserPointRequest::getPoint).sum());
        assertThat(pointHistoryRepository.findAll()).size().isEqualTo(2);
        assertThat(pointHistoryRepository.findAll().stream().map(PointHistory::getPoint).mapToLong(Point::getValue).sum()).isEqualTo(userPointRequestList.stream().mapToLong(UserPointRequest::getPoint).sum());
    }
}