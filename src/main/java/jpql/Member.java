package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Order> orderList;

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

}
