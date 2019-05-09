package com.feiek.cloud.dao;

import com.feiek.cloud.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogDao extends JpaRepository<UserLog,Long>, JpaSpecificationExecutor<UserLog>{
}
