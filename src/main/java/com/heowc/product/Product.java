package com.heowc.product;

import com.heowc.base.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Integer remainingCount;

}
