package com.heowc.book;

import com.heowc.user.domain.Password;
import com.heowc.user.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Chapter8Test {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void test_프록시() {
        User heowc1992 = new User("heowc1992", new Password("!@#$%"), null, null);
        User heowc = new User("heowc", new Password("^&*()"), null, null);

        entityManager.persist(heowc1992);
        entityManager.persist(heowc);

        entityManager.flush();
        entityManager.clear();

        User savedHeowc1992 = entityManager.find(User.class, "heowc1992");
        System.out.println("=============== 너가 먼저 출력 - find ===============");
        System.out.println(savedHeowc1992.getId());
        System.out.println(savedHeowc1992.getPassword().getValue());
        assertThat(savedHeowc1992).hasFieldOrPropertyWithValue("id", "heowc1992");

        User savedHeowc = entityManager.getReference(User.class, "heowc");
        System.out.println("=============== 내가 먼저 출력 - getReference ===============");
        System.out.println(savedHeowc.getId());
        System.out.println(savedHeowc.getPassword().getValue()); // query 발생
        assertThat(savedHeowc).hasFieldOrPropertyWithValue("id", "heowc");
    }

    @Test
    public void test_프록시_실패() {
//        entityManager.getTransaction().begin();
        User heowc = new User("heowc", new Password("^&*()"), null, null);
        entityManager.persist(heowc);

        entityManager.flush();
        entityManager.clear();

        User savedHeowc = entityManager.getReference(User.class, "heowc");

//        entityManager.getTransaction().commit();
        entityManager.close();

//        System.out.println("=============== 내가 먼저 출력 - getReference ===============");
//        assertThatThrownBy(() -> {
//            System.out.println(savedHeowc.getId());
//            System.out.println(savedHeowc.getPassword().getValue()); // query 발생
//        }).isInstanceOf(LazyInitializationException.class);
    }
}
