package com.heowc.book;

import com.heowc.product.Report;
import com.heowc.product.Product;
import com.heowc.user.domain.User;
import com.heowc.user.domain.UserRequest;
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
public class Chapter6Test {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void test_다대다_저장() {
        save();
    }

    @Test
    public void test_다대다_검색() {
        save();

        User heowc = entityManager.find(User.class, "heowc");
        List<Report> reportList = heowc.getReportList();

        assertThat(heowc).isNotNull();
        assertThat(reportList).isNotEmpty();
        assertThat(reportList.get(0).getProduct()).hasFieldOrPropertyWithValue("description", "it is jean");
        assertThat(reportList.get(0).getProduct()).hasFieldOrPropertyWithValue("remainingCount", 100);
    }

    @Test
    public void test_다대다_역검색() {
        save();

        Product jean = entityManager.createQuery("SELECT p FROM Product p WHERE name = :name", Product.class)
                .setParameter("name", "jean")
                .getSingleResult();
        List<Report> reportList = jean.getReportList();

        assertThat(jean).isNotNull();
        assertThat(reportList).isNotEmpty();
        assertThat(reportList.get(0).getUser()).hasFieldOrPropertyWithValue("id", "heowc");
    }

    private void save() {
        Product jean = new Product(null, "jean", "it is jean", 100);
        entityManager.persist(jean);

        User user = new UserRequest("heowc", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();
        entityManager.persist(user);

        Report report = new Report(3L);
        report.setUser(user);
        report.setProduct(jean);

        entityManager.persist(report);

        entityManager.flush();
        entityManager.clear();
    }
}

