package cn.nupt.vo;

import cn.nupt.utils.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 21:01
 */

public class LoginVo {

    private String mobile;
    private String password;

    @NotNull
    @IsMobile
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @NotNull
    @Length(min = 32) //密码最小是32
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
