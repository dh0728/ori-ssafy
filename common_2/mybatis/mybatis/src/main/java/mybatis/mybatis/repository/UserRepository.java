package mybatis.mybatis.repository;

import lombok.RequiredArgsConstructor;
import mybatis.mybatis.domain.User;
import mybatis.mybatis.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public void save(User user) {
        userMapper.insertUser(user);
    }
}
