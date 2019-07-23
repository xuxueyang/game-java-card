package acct.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserInfo {
    @ApiModelProperty(name = "用户标记")
    private Long id;
    @ApiModelProperty(name = "登录名")
    private String name;

    @ApiModelProperty(name = "昵称")
    private String nickName;

    @ApiModelProperty(name = "介绍")
    private String introduce;

    @ApiModelProperty(name = "电话")
    private String tel;

    @ApiModelProperty(name = "邮件")
    private String email;

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
