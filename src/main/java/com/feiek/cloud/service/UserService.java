package com.feiek.cloud.service;

import com.feiek.cloud.entity.Response;
import com.feiek.cloud.entity.Role;
import com.feiek.cloud.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;



public interface UserService extends UserDetailsService {


    User findUserByName(String name);


    Response<User> registUser(User user, Role role);
}
