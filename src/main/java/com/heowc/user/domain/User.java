package com.heowc.user.domain;

import com.heowc.base.domain.BaseEntity;
import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Access(AccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    private String id;

    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private Password password;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    @AttributeOverride(name = "value", column = @Column(name = "total_point"))
    private Point totalPoint;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private List<PointHistory> pointHistory = new ArrayList<>();

    @Version
    private Long version;

    public User(String id, Password password, Name name, Address address) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.address = address;
    }

    public void changePoint(Point point) {
        Long value = Long.sum(totalPoint.getValue(), point.getValue());
        totalPoint = new Point(value);
        pointHistory.add(new PointHistory(point));
    }

    public void clearPassword() {
        String newPassword = generatePassword();
        this.password = new Password(newPassword);
    }

    private String generatePassword() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }


    @PrePersist
    public void prePersist() {
        if (totalPoint == null) {
            totalPoint = new Point();
        }
    }
}
