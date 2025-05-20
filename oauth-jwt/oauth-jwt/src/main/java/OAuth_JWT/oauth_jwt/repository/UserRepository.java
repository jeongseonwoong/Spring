package OAuth_JWT.oauth_jwt.repository;

import OAuth_JWT.oauth_jwt.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    public User findByUsername(String username);
}
