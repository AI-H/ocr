package com.feiek.cloud.controller;

import com.feiek.cloud.security.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    public SecurityUser getCurrentUser(){
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
