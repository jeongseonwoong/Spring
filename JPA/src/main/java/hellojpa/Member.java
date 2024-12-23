package hellojpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name= "TEAM_ID")
//    private Long teamId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<MemberProduct> productList = new ArrayList<>();
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
