package core.manager;

import core.protocol.Protocol;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserObjectManager<T> {
    private int areaL = 0;

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
        return keyObject.get(key);
    }
    public boolean containsValue(String value){
        return valueKey.containsKey(value);
    }
    public Long getKeyByValue(String value){
        return Long.parseLong(valueKey.get(value).split(Protocol.AreaL.split)[0]);
    }

    public void removeByValue(String value) {
        Long aLong = getKeyByValue(value);
        keyValue.remove(aLong);
        keyObject.remove(aLong);
        valueKey.remove(value);
        keyLongs.remove(aLong);
    }
    public void put(Long key,String value,T object){
        keyLongs.add(key);
        valueKey.put(value,key + Protocol.AreaL.split + this.areaL);
        keyValue.put(key + Protocol.AreaL.split + this.areaL,value);
        keyObject.put(key + Protocol.AreaL.split + this.areaL,object);
    }
}
