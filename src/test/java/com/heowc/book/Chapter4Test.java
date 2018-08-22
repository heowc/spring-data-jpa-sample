package com.heowc.book;

import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import com.heowc.user.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Chapter4Test {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void test_단반향_등록() {
        User user = new User("heowc1992", null, null, null);
        User savedUser = entityManager.persist(user);

        PointHistory pointHistory1 = new PointHistory(Point.of(100L));
        pointHistory1.setUser(savedUser);
        entityManager.persist(pointHistory1);

        PointHistory pointHistory2 = new PointHistory(Point.of(300L));
        pointHistory2.setUser(savedUser);
        entityManager.persist(pointHistory2);

        entityManager.flush();
    }

}
