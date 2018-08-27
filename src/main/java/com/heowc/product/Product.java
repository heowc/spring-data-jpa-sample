package com.heowc.product;

import com.heowc.base.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Integer remainingCount;

    @Setter
    @OneToMany(mappedBy = "product")
    private List<Report> reportList = new ArrayList<>();

    public Product(Long id, String name, String description, Integer remainingCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.remainingCount = remainingCount;
    }
}
