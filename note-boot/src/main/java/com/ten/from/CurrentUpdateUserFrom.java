package com.ten.from;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 当前用户修改入参
 * @author shisen
 */
@Data
public class CurrentUpdateUserFrom {

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(regexp="^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "邮箱格式不正确")
    private String email;
}
