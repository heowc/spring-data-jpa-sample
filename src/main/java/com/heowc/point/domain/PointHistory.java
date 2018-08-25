package com.heowc.point.domain;

import com.heowc.base.domain.BaseEntity;
import com.heowc.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @AttributeOverride(name = "value", column = @Column(name="point", nullable = false))
    private Point point = new Point();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addUser(User user) {
        if (this.user != null) {
            this.user.getPointHistoryList().remove(this);
        }

        this.user = user;
        user.getPointHistoryList().add(this);
    }

    public PointHistory(Point point) {
        this.point = point;
    }
}
