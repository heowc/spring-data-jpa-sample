package com.heowc.user.domain;

import com.heowc.base.domain.BaseEntity;
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
    private String name;
    private String address;
    private String zipCode;
    private Long totalPoint = 0L;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<PointHistory> pointHistory = new ArrayList<>();

    @Version
    private Long version;

    public User(String id, String name, String address, String zipCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
    }

    public void changePoint(Long point) {
        totalPoint = Long.sum(totalPoint, point);
        pointHistory.add(new PointHistory(point));
    }

    @PrePersist
    public void prePersist() {
        if (totalPoint == null) {
            totalPoint = 0L;
        }
    }
}
