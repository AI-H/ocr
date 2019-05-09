package com.feiek.cloud.service.impl;

import com.feiek.cloud.dao.UserDao;
import com.feiek.cloud.entity.Response;
import com.feiek.cloud.entity.Role;
import com.feiek.cloud.entity.User;
import com.feiek.cloud.security.SecurityUser;
import com.feiek.cloud.service.UserService;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Transactional
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao dao;


    @Override
    public User findUserByName(String name) {

        Optional<User> userByName = dao.findOne((root, q, cb) ->
            cb.equal(root.get("name"), name)
        );

        User user = userByName.get();

        List<Role> roles = user.getRoles();

        Collections.sort(roles);


        return user;
    }

    /**
     * 用户注册
     * @return
     */
    @Override
    public Response<User> registUser(User user, Role role) {
        User userByName = this.findUserByName(user.getName());
        if (userByName!=null){
            return Response.failed("已经注册过了",userByName);
        }else{
            if (role!=null){
                role=Role.DEFAULT_ROLE;
            }

            user.getRoles().add(role);
            User save = dao.save(user);
            return Response.success(save);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.findUserByName(username);

        List<Role> roles = user.getRoles();
        Role role = roles.get(0);
        SecurityUser securityUser = new SecurityUser();
        securityUser.setPassword(user.getPassword());
        securityUser.setRole(role.getName());
        securityUser.setUsername(user.getName());


        return securityUser;
    }
}
