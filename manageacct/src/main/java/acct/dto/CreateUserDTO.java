package acct.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class CreateUserDTO {
    @NotNull
    private Long area;
    @NotNull
    private String nickName;


    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
