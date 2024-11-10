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
//@TableGenerator(name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCES",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 50)
//@Setter
//@Getter
//@ToString
//public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="member_seq_generator")
//    private Long id;
//    @Column(name = "name")
//    private String username;
//    private Integer age;
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//    @Lob
//    private String description;
//    //Getter, Setter…
//    public Member() {
//
//    }
//
//}
