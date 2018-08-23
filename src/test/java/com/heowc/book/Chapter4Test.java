package com.heowc.book;

import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import com.heowc.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Chapter4Test {

    @Autowired
    private EntityManager entityManager;

    @Before
    public void before_기초_데이터_삽입() {
        User user = new User("heowc1992", null, null, null);
        entityManager.persist(user);

        PointHistory pointHistory1 = new PointHistory(Point.of(100L));
        pointHistory1.setUser(user);
        entityManager.persist(pointHistory1);

        PointHistory pointHistory2 = new PointHistory(Point.of(300L));
        pointHistory2.setUser(user);
        entityManager.persist(pointHistory2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void test_단반향_등록() {
        User heowc1992 = entityManager.find(User.class, "heowc1992");
        assertThat(heowc1992).isNotNull();
        assertThat(heowc1992.getTotalPoint().getValue()).isEqualTo(0L);
    }

    @Test
    public void test_단반향_jpql를_활용한_조회() {
        List<PointHistory> pointHistoryList = entityManager.createQuery("SELECT ph FROM PointHistory ph JOIN ph.user u WHERE u.id = :id", PointHistory.class)
                .setParameter("id", "heowc1992")
                .getResultList();

        assertThat(pointHistoryList).isNotEmpty();
        assertThat(pointHistoryList).size().isEqualTo(2);
    }

    @Test
    public void test_단반향_연관관계_수정() {
        User user = new User("heowc", null, null, null);
        entityManager.persist(user);

        PointHistory pointHistory = entityManager.createQuery("SELECT ph FROM PointHistory ph JOIN ph.user u WHERE u.id = :id", PointHistory.class)
                .setParameter("id", "heowc1992")
                .setMaxResults(1)
                .getSingleResult();
        pointHistory.setUser(user);

        entityManager.persist(pointHistory);
        entityManager.flush();

        PointHistory updatedPointHistory = entityManager.find(PointHistory.class, 1L);
        assertThat(updatedPointHistory).isNotNull();
        assertThat(updatedPointHistory.getUser()).isNotNull();
        assertThat(updatedPointHistory.getUser().getId()).isEqualTo(entityManager.find(User.class, "heowc").getId());
    }

    @Test
    public void test_단반향_연관관계_제거() {
        PointHistory pointHistory = entityManager.createQuery("SELECT ph FROM PointHistory ph JOIN ph.user u WHERE u.id = :id", PointHistory.class)
                .setParameter("id", "heowc1992")
                .setMaxResults(1)
                .getSingleResult();
        pointHistory.setUser(null);

        entityManager.persist(pointHistory);
        entityManager.flush();
    }

    @Test
    public void test_일대다_컬렉션_조회() {
        User heowc1992 = entityManager.find(User.class, "heowc1992");
        heowc1992.getPointHistoryList().forEach(ph -> System.out.println("point value = " + ph.getPoint().getValue()));

        assertThat(heowc1992.getPointHistoryList().stream().map(PointHistory::getPoint).mapToLong(Point::getValue).sum()).isEqualTo(400L);
    }
}
