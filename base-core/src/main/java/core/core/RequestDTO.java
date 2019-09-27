package core.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.redis.util.ByteUtils;

import java.io.Serializable;

public class RequestDTO<Data> implements Serializable {
    //0级做保留
    @ApiModelProperty(required = true)
    private int type;//二级协议（区分模块等）
    @ApiModelProperty(required = true)
    private int area;//一级协议(区分大区等.NEW:顶级协议）
    @ApiModelProperty(required = true)
    private long areaL;//大区
    private Integer protocol;//三级具体协议
    private Data data;
    @ApiModelProperty(required = true)
    private Long timestamp;
//    transient
    @ApiModelProperty(name = "由token+時間戳，md5加密組成。" +
            "相同的用戶和token的情況下：不允許出現同樣的時間戳，碰撞概率反正很小",
            required = true
    )
    private String md5;
    private int messageType;
    private int roomOperatorLong;//包序列
    private long userId;
    private String roomId;



    public static byte[] toByteArray(RequestDTO dto){
        //序列化自己
        byte[] bytes = JSON.toJSONBytes(dto);
//        return intToByte(bytes);
//        byte[][] bytes1 = ByteUtils.mergeArrays(first, bytes);
        return bytes;
    }

    public static RequestDTO toObject(byte[] bytes){
        Object object = JSON.parseObject(bytes, RequestDTO.class);//JSONObject.parse(bytes, Feature.IgnoreNotMatch );
//        Object object1 = JSON.parseObject(bytes, RequestDTO.class);
        return (RequestDTO)object;
    }

    public static boolean verify(RequestDTO dto){
        if(dto==null||dto.type==0||dto.area==0||dto.timestamp==null||dto.md5==null)
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

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
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



    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAreaL() {
        return areaL;
    }

    public void setAreaL(long areaL) {
        this.areaL = areaL;
    }

    public int getRoomOperatorLong() {
        return roomOperatorLong;
    }

    public void setRoomOperatorLong(int roomOperatorLong) {
        this.roomOperatorLong = roomOperatorLong;
    }
}
