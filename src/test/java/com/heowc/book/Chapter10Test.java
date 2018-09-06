package com.heowc.book;

import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import com.heowc.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Chapter10Test {

    @PersistenceContext
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
    public void test_페치조인_결과() {
        List<PointHistory> pointHistoryList =
                entityManager.createQuery("SELECT ph From PointHistory ph JOIN FETCH ph.user u WHERE u.id = :id", PointHistory.class)
                        .setParameter("id", "heowc1992")
                        .getResultList();

        User user = pointHistoryList.get(0).getUser();
        assertThat(user).hasFieldOrPropertyWithValue("id", "heowc1992");
        User user2 = pointHistoryList.get(1).getUser();
        assertThat(user2).hasFieldOrPropertyWithValue("id", "heowc1992");

        /*
            Hibernate:
                select
                    pointhisto0_.id as id1_1_0_,
                    user1_.id as id1_4_1_,
                    pointhisto0_.created_date as created_2_1_0_,
                    pointhisto0_.last_modified_date as last_mod3_1_0_,
                    pointhisto0_.point as point4_1_0_,
                    pointhisto0_.user_id as user_id5_1_0_,
                    user1_.created_date as created_2_4_1_,
                    user1_.last_modified_date as last_mod3_4_1_,
                    user1_.address1 as address4_4_1_,
                    user1_.address2 as address5_4_1_,
                    user1_.zip_code as zip_code6_4_1_,
                    user1_.mall_id as mall_id12_4_1_,
                    user1_.first_name as first_na7_4_1_,
                    user1_.last_name as last_nam8_4_1_,
                    user1_.password as password9_4_1_,
                    user1_.total_point as total_p10_4_1_,
                    user1_.version as version11_4_1_
                from
                    point_history pointhisto0_
                inner join
                    user user1_
                        on pointhisto0_.user_id=user1_.id
                where
                    user1_.id=?
         */
    }
}
