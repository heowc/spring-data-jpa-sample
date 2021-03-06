package com.heowc.user.domain;

import com.heowc.base.domain.BaseEntity;
import com.heowc.mall.domain.Mall;
import com.heowc.point.domain.Point;
import com.heowc.point.domain.PointHistory;
import com.heowc.product.Report;
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

    @AttributeOverride(name = "value", column = @Column(name = "total_point", nullable = false))
    private Point totalPoint = Point.of(0L);

    @OneToMany(mappedBy = "user")
    private List<PointHistory> pointHistoryList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Mall mall;

    @OneToMany(mappedBy = "user")
    private List<Report> reportList = new ArrayList<>();

    @Version
    private Long version;

    public User(String id, Password password, Name name, Address address) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.address = address;
    }

    public void addTotalPoint(Point point) {
        totalPoint = new Point(totalPoint.getValue() + point.getValue());
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

    public void changeAddress(Address address) {
        this.address = address;
    }
}
