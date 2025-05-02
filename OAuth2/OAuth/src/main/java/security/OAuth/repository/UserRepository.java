package security.OAuth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import security.OAuth.Entity.User;

//@Repository 어노테이션이 없어도 IOC됨. JpaRepository를 상속했기 때문에.
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUsername(String username);
}
