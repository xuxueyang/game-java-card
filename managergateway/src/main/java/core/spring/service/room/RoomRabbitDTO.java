package core.spring.service.room;


public class RoomRabbitDTO<Data> {
    private Long userId;//發送人
    private Byte type;//一级协议(区分大区等）
    private Byte area;//二级协议（区分模块等）
    private Integer protocol;//三级具体协议
    private Data data;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
