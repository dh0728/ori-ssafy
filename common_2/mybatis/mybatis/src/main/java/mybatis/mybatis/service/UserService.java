package mybatis.mybatis.service;

import lombok.RequiredArgsConstructor;
import mybatis.mybatis.domain.User;
import mybatis.mybatis.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }


}
