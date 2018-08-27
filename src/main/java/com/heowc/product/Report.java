package com.heowc.product;

import com.heowc.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long count;

    public Report(Long count) {
        this.count = count;
    }

    public void setUser(User user) {
        this.user = user;
        user.getReportList().add(this);
    }

    public void setProduct(Product product) {
        this.product = product;
        product.getReportList().add(this);
    }
}
