package mybatis.mybatis.mapper;

import mybatis.mybatis.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User findById(@Param("id") Long id);
    void insertUser(User user);

}
