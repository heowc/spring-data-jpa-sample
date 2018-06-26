package com.heowc.point.domain;

import com.heowc.base.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointHistory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @AttributeOverride(name = "value", column = @Column(name="point"))
    private Point point;

    public PointHistory(Point point) {
        this.point = point;
    }
}
