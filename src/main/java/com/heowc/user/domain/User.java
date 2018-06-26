package com.heowc.user.domain;

import com.heowc.base.domain.BaseEntity;
import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    @Id
    private String id;
    @Embedded
    private Name name;
    @Embedded
    private Address address;
    
    @AttributeOverride(name = "value", column = @Column(name="total_point"))
    private Point totalPoint;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<PointHistory> pointHistory = new ArrayList<>();

    @Version
    private Long version;

    public User(String id, Name name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public void changePoint(Point point) {
        Long value = Long.sum(totalPoint.getValue(), point.getValue());
        totalPoint = new Point(value);
        pointHistory.add(new PointHistory(point));
    }

    @PrePersist
    public void prePersist() {
        if (totalPoint == null) {
            totalPoint = new Point();
        }
    }
}
