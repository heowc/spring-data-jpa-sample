package com.heowc.book;

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
    public void test_다대다_단방향_저장() {
        save();
    }

    @Test
    public void test_다대다_단방향_검색() {
        save();

        User heowc = entityManager.find(User.class, "heowc");
        List<Product> productList = heowc.getProductList();

        assertThat(heowc).isNotNull();
        assertThat(productList).isNotEmpty();
        assertThat(productList).element(0).hasFieldOrPropertyWithValue("name", "jean");
        assertThat(productList).element(0).hasFieldOrPropertyWithValue("description", "it is jean");
        assertThat(productList).element(0).hasFieldOrPropertyWithValue("remainingCount", 100);
    }

    private void save() {
        Product jean = new Product(null, "jean", "it is jean", 100);
        entityManager.persist(jean);

        User user = new UserRequest("heowc", "!@#$%", "wonchul", "heo", "12345", "Asia", "Seoul").toUser();
        user.getProductList().add(jean);
        entityManager.persist(user);

        entityManager.flush();
    }
}

