package personal.practice.domain;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id //JPA 사용시 필요한 어노테이션들
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db가 id를 알아서 설정해준다
    private Long id;
    private String name;

//    @Column(name ="name") db의 데이터와 연결 변수와 이름이 다를 시 사용
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
