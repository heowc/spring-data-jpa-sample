package com.heowc;

import com.heowc.user.domain.User;
import com.heowc.user.domain.UserForPoint;
import com.heowc.user.domain.UserRepository;
import com.heowc.user.service.ChangingPointService;
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
public class ChangingPointServiceTest {

    @Autowired
    private ChangingPointService service;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void changeSuccess() {
        // given
        User user = new User("heowc1992", null, null);
        userRepository.save(user);

        List<UserForPoint> userForPointList = Arrays.asList(
                new UserForPoint(user.getId(), 100L),
                new UserForPoint(user.getId(), 300L)
        );
        userForPointList.forEach(userForPoint -> service.change(userForPoint));
        userRepository.flush();

        // when
        User changedUser = userRepository.findById(user.getId()).get();

        // then
        assertThat(changedUser).isNotNull();
        assertThat(changedUser.getTotalPoint().getValue()).isEqualTo(userForPointList.stream().mapToLong(UserForPoint::getPoint).sum());
        assertThat(changedUser.getPointHistory()).size().isEqualTo(2);
        assertThat(changedUser.getTotalPoint().getValue()).isEqualTo(changedUser.getPointHistory().stream().mapToLong(ph -> ph.getPoint().getValue()).sum());
    }
}