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
    private Byte type;//二级协议（区分模块等）
    @ApiModelProperty(required = true)
    private Byte area;//一级协议(区分大区等）
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
    private Integer index;//包序列
    private Long userId = null;
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
        if(dto==null||dto.type==null||dto.area==null||dto.timestamp==null||dto.md5==null)
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




    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
