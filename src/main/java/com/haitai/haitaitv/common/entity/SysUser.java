package com.haitai.haitaitv.common.entity;

import org.beetl.sql.core.annotatoin.ColumnIgnore;
import org.beetl.sql.core.annotatoin.TableTemplate;

import java.time.LocalDateTime;

/*
* 
* gen by beetlsql 2017-03-23
*/
@TableTemplate("ORDER BY id")
public class SysUser extends org.beetl.sql.core.TailBean {
    //主键
    private Integer id;
    //用户名/11111
    private String username;
    //密码
    private String password;
    //真实姓名
    private String realname;
    //部门/11111/dict
    private Integer departmentId;
    //类型//select/1,管理员,2,普通用户,3,前台用户,4,第三方用户
    private Integer type;
    //状态
    private Integer state;
    //第三方ID
    private String thirdId;
    //结束时间
    private LocalDateTime endTime;
    //email
    private String email;
    //手机号
    private String tel;
    //地址
    private String address;
    //头像地址
    private String titleUrl;
    //说明
    private String remark;
    //主题
    private String theme;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;
    //加密密码使用的盐
    private String salt;

    // 下面的字段不参与insert和update
    private String operatorId;

    public SysUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @ColumnIgnore(insert = false)
    public String getOperatorId() {
        // 防止错误的使用方式，便于查错
        if (operatorId == null) {
            throw new UnsupportedOperationException("尚未调用setOperatorId方法");
        }
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        // 保证operatorId在调用了set方法后一定不是null
        if (operatorId == null) {
            this.operatorId = "";
        } else {
            this.operatorId = operatorId;
        }
    }
}
