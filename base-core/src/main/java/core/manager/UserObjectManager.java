package core.manager;

import core.protocol.Protocol;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserObjectManager<T> {
    private int areaL = 0;
    public int  getAreaL(){
        return areaL;
    }
    private  ConcurrentHashMap<String,String> valueKey = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<String,String> keyValue = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<String,T> keyObject = new ConcurrentHashMap<>();

    private  final List<Long> keyLongs = new LinkedList<>();

    public UserObjectManager(int areaL){
        this.areaL = areaL;
    }
    public boolean isEmpty(){
        return keyValue.isEmpty();
    }
    public List<Long> keys(){
        return keyLongs;
    }
    public T getObject(Long key){
        return keyObject.get(key + Protocol.AreaL.split + this.areaL);
    }
    public boolean containsValue(String value){
        return valueKey.containsKey(value);
    }
    public Long getKeyByValue(String value){
        if(value==null){
            return null;
        }
        if(valueKey.containsKey(value)){
            return Long.parseLong(valueKey.get(value).split(Protocol.AreaL.split)[0]);
        }
        return null;
    }

    public void removeByValue(String value) {
        Long aLong = getKeyByValue(value);
        if(aLong!=null){
            keyValue.remove(aLong+ Protocol.AreaL.split + this.areaL);
            keyObject.remove(aLong + Protocol.AreaL.split + this.areaL);
            valueKey.remove(value);
            keyLongs.remove(aLong);
        }
    }
    public void put(Long key,String value,T object) throws Exception {
        if(key==null){
            throw new Exception("key为空");
        }
        keyLongs.add(key);
        valueKey.put(value,key + Protocol.AreaL.split + this.areaL);
        keyValue.put(key + Protocol.AreaL.split + this.areaL,value);
        keyObject.put(key + Protocol.AreaL.split + this.areaL,object);
    }
}
