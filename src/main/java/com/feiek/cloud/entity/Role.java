package com.feiek.cloud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements Serializable, Comparable<Role>{



    private static final long serialVersionUID = 1594020539147910453L;

    public static Role DEFAULT_ROLE =new Role("鼠","M","anno","无",1);
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "name")
    private String  name;

    @Column(name = "code")
    private String  code;

    @Column(name = "type")
    private String  type;

    @Column(name = "remark")
    private String  remark;


    //    角色状态：0-注销，1-正常，2-注销
    @Column(name = "statu")
    private Integer  statu;


    @ManyToMany
    @JoinTable(name = "user_role",joinColumns = {
            @JoinColumn(name = "role_id",referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    })
    private List<User> users = new ArrayList<>(0);


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role() {
    }


    public Role(String name, String code, String type, String remark, Integer statu) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.remark = remark;
        this.statu = statu;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", statu=" + statu +
                ", users=" + users +
                '}';
    }

    @Override
    public int compareTo(Role o) {
        return Integer.compare(this.id,o.id);
    }
}
