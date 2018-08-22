package com.heowc.book;

import com.heowc.point.domain.Point;
import com.heowc.user.domain.Address;
import com.heowc.user.domain.User;
import com.heowc.user.domain.UserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Chapter3Test {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void entityManager() {
        assertThat(entityManager).isNotNull();
    }

    @Test
    public void lifecycle() {
        // 비영속
        System.out.println("=============== 비영속 ===============");
        User user = new UserRequest("heowc1992", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();

        // 영속
        System.out.println("=============== 영속 ===============");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        // 준영속
        System.out.println("=============== 준영속 ===============");
        User heowc1992 = entityManager.find(User.class, "heowc1992");
        entityManager.detach(heowc1992);
        heowc1992.changeAddress(new Address("12345", "Asia", "Busan"));
        entityManager.flush(); // not update
        entityManager.clear();

        // 삭제
        System.out.println("=============== 삭제 ===============");
        heowc1992 = entityManager.find(User.class, "heowc1992");
        entityManager.remove(heowc1992);
        entityManager.flush();
    }

}
