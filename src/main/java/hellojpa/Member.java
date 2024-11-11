//package hellojpa;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Setter
//@Getter
//@ToString
//public class Member {
//    @Id @GeneratedValue
//    @Column(name = "MEMBER_ID")
//    private Long id;
//
//    @Column(name = "USERNAME")
//    private String username;
//
////    @Column(name= "TEAM_ID")
////    private Long teamId;
//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID")
//    private Team team;
//
//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }
//}
