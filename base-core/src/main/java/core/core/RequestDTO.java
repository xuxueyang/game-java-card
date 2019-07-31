package core.core;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class RequestDTO<Data> implements Serializable {
    //0级做保留
    @ApiModelProperty(required = true)
    private Byte type;//一级协议(区分大区等）
    @ApiModelProperty(required = true)
    private Byte area;//二级协议（区分模块等）
    private Integer protocol;//三级具体协议
    private Data data;
    @ApiModelProperty(required = true)
    private Long timestamp;
    @ApiModelProperty(name = "由token+時間戳，md5加密組成。" +
            "相同的用戶和token的情況下：不允許出現同樣的時間戳，碰撞概率反正很小",
            required = true
    )
    private String md5;
    private Byte messageType;

    public boolean verify(){
        if(type==null||area==null||timestamp==null||md5==null)
            return false;
        return true;
    }
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getArea() {
        return area;
    }

    public void setArea(Byte area) {
        this.area = area;
    }

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
