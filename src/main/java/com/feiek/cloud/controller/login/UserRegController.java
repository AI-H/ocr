package com.feiek.cloud.controller.login;


import com.feiek.cloud.controller.BaseController;
import com.feiek.cloud.entity.Response;
import com.feiek.cloud.entity.Role;
import com.feiek.cloud.entity.User;
import com.feiek.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserRegController extends BaseController {

    @Autowired
    private UserService service;

    @GetMapping("regist")
    public Response<User>  registUser(User user, Role role){
        return service.registUser(user,role);
    }
}
