package com.crm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kazama
 * @create 2021-03-04-22:52
 */
@Data
public class User implements Serializable {
    private String id;  //编号 主键
    private String loginAct;  //登录账号
    private String name;  //用户真实姓名
    private String loginPwd;  //登录密码
    private String email;  //邮箱
    private String expireTime;  //失效时间 19位 年月日 时分秒
    private String lockState;  //锁定状态 0:锁定 1:启用
    private String deptno;  //部门编号
    private String allowIps;  //允许访问的ip地址
    private String createTime;  //创建时间 19位
    private String createBy;  //创建人
    private String editTime;  //修改时间 19位
    private String editBy;  //修改人
}
