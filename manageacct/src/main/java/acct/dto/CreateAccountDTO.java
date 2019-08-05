package acct.dto;

import javax.validation.constraints.NotNull;

public class CreateAccountDTO {
    @NotNull
    private String loginName;
    @NotNull
    private String email;
    @NotNull
    private String password;//md5加密

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
