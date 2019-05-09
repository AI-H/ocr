package com.feiek.cloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 飞客不去
 * @date  创建时间 2019/5/8 16:52
 * @version 1.0
 * @description 操作日志
 */
@Entity
@Table(name = "user_log")
public class UserLog implements Serializable{


    private static final long serialVersionUID = 601859912334743090L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "interface")
    private String func;

    @Column(name = "sta_time")
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public UserLog() {
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", func='" + func + '\'' +
                ", time=" + time +
                '}';
    }
}
