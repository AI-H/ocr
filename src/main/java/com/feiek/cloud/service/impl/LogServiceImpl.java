package com.feiek.cloud.service.impl;

import com.feiek.cloud.dao.LogDao;
import com.feiek.cloud.entity.User;
import com.feiek.cloud.entity.UserLog;
import com.feiek.cloud.security.SecurityUser;
import com.feiek.cloud.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService{

    @Autowired
    private LogDao dao;

    @Override
    public int saveLog(SecurityUser user, String company) {
        UserLog userLog = new UserLog();
        userLog.setFunc(company);
        userLog.setName(user.getUsername());
        userLog.setTime(new Date());
        UserLog save = dao.save(userLog);
        return 1;
    }



}
