package com.ankoye.jelly.oauth;

/**
 * 看自己怎么设计的，这里只设计了两张表
 * 可以设计：用户表、角色表、权限表、用户角色表、角色权限表
 */
public class SysPermission {
    private Integer id;
    private String url;     // 授权url
    private Integer roleId; // 授权角色

    public SysPermission() {
    }

    public SysPermission(Integer id, String url, Integer roleId) {
        this.id = id;
        this.url = url;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
