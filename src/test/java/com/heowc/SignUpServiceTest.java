package com.heowc;

import com.heowc.user.domain.UserRepository;
import com.heowc.user.domain.UserRequest;
import com.heowc.user.service.SignUpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SignUpServiceTest {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserRepository repository;

    @Test
    public void joinSuccess() {
        // given
        String id = "heowc1992";
        UserRequest request = new UserRequest(id, "heowc", "Seoul/Asia", "12345");

        // when
        signUpService.join(request.toUser());

        // then
        assertThat(repository.existsById(id)).isTrue();
        repository.findById(id).ifPresent(System.out::println);
    }

    @Test
    public void joinFail() {
        // given
        String id = "heowc1992";
        UserRequest request = new UserRequest(id, "heowc", "Seoul", "12345");
        signUpService.join(request.toUser());

        // when-then
        assertThatThrownBy(() -> {
            UserRequest request2 = new UserRequest(id, "heo won chul", "Busan", "98765");
            signUpService.join(request2.toUser());
        }).isInstanceOf(ResponseStatusException.class);
    }
}
