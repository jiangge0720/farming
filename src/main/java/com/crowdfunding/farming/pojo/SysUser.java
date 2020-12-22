package com.crowdfunding.farming.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 * @author Jiang-gege
 * 2020/10/3114:06
 */
@Data
@Table(name = "sys_user")
public class SysUser {

    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer userId;

    private String userName;

    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String mobilePhone;

    private String idNo;

    private String idType;

    private String userImagePath;

    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
    private String nickName;

    @JsonIgnore
    @Length(min = 6, max = 11, message = "密码只能在6~11位之间")
    private String password;

    private Integer cityId;

    private String cityName;
}