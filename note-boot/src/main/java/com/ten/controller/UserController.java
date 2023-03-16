package com.ten.controller;

import com.ten.annotation.SecurityParameter;
import com.ten.constant.CommonConstant;
import com.ten.entity.SysUser;
import com.ten.enums.ResultEnum;
import com.ten.from.CurrentUpdateUserFrom;
import com.ten.from.IdFrom;
import com.ten.from.UpdatePasswordFrom;
import com.ten.service.SysUserService;
import com.ten.utils.AesUtil;
import com.ten.utils.JwtUtil;
import com.ten.vo.BaseResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * 用户模块
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private SysUserService userService;

    /**
     * description  修改密码
     * @param       updatePasswordFrom
     * @return      com.ten.vo.BaseResultVO
     * @author      shisen
     * date         2022/2/2 17:13
     */
    @PostMapping("/update/password")
    @SecurityParameter(outEncode = false)
    public BaseResultVO forgetPassword(@Valid @RequestBody UpdatePasswordFrom updatePasswordFrom){
        SysUser user = this.userService.getById(JwtUtil.getUserId());
        if(ObjectUtils.isEmpty(user)){
            return BaseResultVO.fail(ResultEnum.USER_NOT_EXISTENT);
        }
        String encryptPassword=null;
        try {
            encryptPassword = AesUtil.encrypt(updatePasswordFrom.getOldPassword(), CommonConstant.PASSWORD_KEY, false);
            if(!user.getPassword().equals(encryptPassword)){
                return BaseResultVO.fail(ResultEnum.USER_OLD_PASSWORD_ERROR);
            }
            encryptPassword = AesUtil.encrypt(updatePasswordFrom.getNewPassword(), CommonConstant.PASSWORD_KEY, false);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResultVO.fail(e.getMessage());
        }
        user.setPassword(encryptPassword);
        user.setUpdateTime(new Date());
        this.userService.updateById(user);
        return BaseResultVO.success();
    }

    /**
     * description  查看用户
     * @param       idFrom
     * @return      com.ten.vo.BaseResultVO
     * @author      shisen
     * date         2022/2/2 17:13
     */
    @PostMapping("/info")
    @SecurityParameter
    public BaseResultVO getInfo(@Valid @RequestBody IdFrom idFrom){
        SysUser user = this.userService.getById(idFrom.getId());
        if(ObjectUtils.isEmpty(user)){
            return BaseResultVO.fail(ResultEnum.USER_NOT_EXISTENT);
        }
        user.setPassword(null);
        return BaseResultVO.success(user);
    }

    /**
     * description  查看登录用户信息
     * @return      com.ten.vo.BaseResultVO
     * @author      shisen
     * date         2022/2/2 17:13
     */
    @PostMapping("/current/info")
    @SecurityParameter(inDecode=false)
    public BaseResultVO getCurrentInfo(){
        SysUser user = this.userService.getById(JwtUtil.getUserId());
        if(ObjectUtils.isEmpty(user)){
            return BaseResultVO.fail(ResultEnum.USER_NOT_EXISTENT);
        }
        user.setPassword(null);
        return BaseResultVO.success(user);
    }

    /**
     * description  更新当前登录用户信息
     * @return      com.ten.vo.BaseResultVO
     * @author      shisen
     * date         2022/2/2 17:13
     */
    @PutMapping("/current/update")
    @SecurityParameter(outEncode=false)
    public BaseResultVO updateCurrentUser(@Valid @RequestBody CurrentUpdateUserFrom currentUpdateUserFrom){
        SysUser user = this.userService.getById(JwtUtil.getUserId());
        if(ObjectUtils.isEmpty(user)){
            return BaseResultVO.fail(ResultEnum.USER_NOT_EXISTENT);
        }
        user.setNickname(currentUpdateUserFrom.getNickName());
        user.setEmail(currentUpdateUserFrom.getEmail());
        this.userService.updateById(user);
        return BaseResultVO.success();
    }
}
