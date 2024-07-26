package hello.servlet.domain.member;

import lombok.Data;

@Data
public class Member {

    private Long id;
    private String userName;
    private int age;

    public Member(){

    }

    public Member(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }
}
