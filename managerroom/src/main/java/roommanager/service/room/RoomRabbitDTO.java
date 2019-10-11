package roommanager.service.room;


public class RoomRabbitDTO<Data> {
    private Long userId;//發送人
    private int type;//一级协议(区分大区等）
    private int area;//二级协议（区分模块等）
    private Integer protocol;//三级具体协议
    private Data data;
    private int roomOperatorLong;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
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

    public int getRoomOperatorLong() {
        return roomOperatorLong;
    }

    public void setRoomOperatorLong(int roomOperatorLong) {
        this.roomOperatorLong = roomOperatorLong;
    }
}
