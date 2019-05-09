package com.feiek.cloud.service;

import com.feiek.cloud.entity.User;
import com.feiek.cloud.security.SecurityUser;

public interface LogService {

     int saveLog(SecurityUser user, String company);

}
