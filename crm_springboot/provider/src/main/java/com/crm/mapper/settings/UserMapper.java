package com.crm.mapper.settings;

import com.crm.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-04-22:55
 */
public interface UserMapper {
    /**账号密码获得用户**/
    User login(Map<String, Object> map);

    /**用户列表**/
    List<User> getUserList();
}
