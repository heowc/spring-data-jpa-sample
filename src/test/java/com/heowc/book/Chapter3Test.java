package com.heowc.book;

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
    public void test_잘_가져왔나() {
        assertThat(entityManager).isNotNull();
    }

    @Test
    public void test_엔티티_생명주기() {
        System.out.println("=============== 비영속 ===============");
        User user = new UserRequest("heowc1992", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();

        System.out.println("=============== 영속 ===============");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        System.out.println("=============== 준영속 ===============");
        User heowc1992 = entityManager.find(User.class, "heowc1992");
        entityManager.detach(heowc1992);
        heowc1992.changeAddress(new Address("12345", "Asia", "Busan"));
        entityManager.flush(); // not update
        entityManager.clear();

        System.out.println("=============== 삭제 ===============");
        heowc1992 = entityManager.find(User.class, "heowc1992");
        entityManager.remove(heowc1992);
        entityManager.flush();
    }

    @Test
    public void test_1차_캐시_및_동일성_보장() {
        User user = new UserRequest("heowc1992", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();
        entityManager.persist(user);

        User user1 = entityManager.find(User.class, "heowc1992");
        User user2 = entityManager.find(User.class, "heowc1992");

        assertThat(user1).isEqualTo(user2);
    }

    @Test
    public void test_지연_등록() {
        User user = new UserRequest("heowc1992", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();
        User user2 = new UserRequest("heowc", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();

        entityManager.persist(user);
        entityManager.persist(user2);
        System.out.println("=============== 아직 insert 안됨 ===============");

        entityManager.flush();

        /*

        =============== 아직 insert 안됨 ===============
        Hibernate:
            insert
            into
        ...

         */
    }

    @Test
    public void test_변경_감지() {
        User user1 = new UserRequest("heowc1992", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();
        User savedUser1 = entityManager.persist(user1);
        savedUser1.changeAddress(new Address("12345", "Asia", "Busan")); // update 발생 함

        User user2 = new UserRequest("heowc", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();
        User savedUser2 = entityManager.persist(user2);
        savedUser2.changeAddress(new Address("12345", "Asia", "Busan"));
        savedUser2.changeAddress(new Address("12345", "Asia", "Seoul")); // update 발생 안함

        entityManager.flush();
        entityManager.clear();

        User heowc1992 = entityManager.find(User.class, "heowc1992");
        User heowc = entityManager.find(User.class, "heowc");

        assertThat(heowc1992).isNotNull();
        assertThat(heowc).isNotNull();
        assertThat(heowc1992.getAddress().getAddress2()).isEqualTo("Busan");
        assertThat(heowc.getAddress().getAddress2()).isEqualTo("Seoul");
    }
}
