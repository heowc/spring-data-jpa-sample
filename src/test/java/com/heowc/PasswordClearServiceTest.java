package com.heowc;

import com.heowc.user.domain.Password;
import com.heowc.user.domain.User;
import com.heowc.user.domain.UserRepository;
import com.heowc.user.service.PasswordClearService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest

public class PasswordClearServiceTest {

    @Autowired
    private PasswordClearService service;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    public void clearSuccess() {
        // given
        String id = "heowc1992";
        Password password = new Password("12345");
        userRepository.saveAndFlush(new User(id, password, null, null));

        // when
        User clearUser = userRepository.findById(id).get();
        service.clear(clearUser.getId());

        // then
        assertThat(clearUser).isNotNull();
        assertThat(password).isNotEqualTo(clearUser.getPassword());
    }
}
