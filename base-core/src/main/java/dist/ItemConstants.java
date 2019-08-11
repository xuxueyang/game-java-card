package dist;
public class ItemConstants {

    private static int _Attribute = 1000;

    public static Attribute getAttributeByCode(int code){
        Attribute[] values = Attribute.values();
        for(int i=0;i<values.length;i++){
            if(values[i].getIndex() == code){
                return values[i];
            }
        }
        return null;
    }
    public enum Attribute{
        JIN("金",1),
        MU("木",2),
        SHUI("水",3),
        HUO("火",4),
        TU("土",5);

        //成员变量
        private String name;
        private int index;
        //构造方法
        private Attribute(String name,int code)
        {
            this.name=name;
            this.index=code+_Attribute;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }

    public static enum Type{
        ENVOY,
        CARD
    }
    public static enum likeType{
        ATTACK,
        Defense,
        Blood
    }
    public static Grade getGradeByCode(int code){
        Grade[] values = Grade.values();
        for(int i=0;i<values.length;i++){
            if(values[i].getIndex() == code){
                return values[i];
            }
        }
        return null;
    }
    private static int _Grade = 2000;
    public enum Grade{
        R("R",1,100),
        S("S",2,120),
        SS("SS",3,150),
        SSR("SSR",4,180);

        //成员变量
        private String name;
        private int starForce;
        private int index;
        //构造方法
        private Grade(String name,int index,int starForce)
        {
            this.name=name;
            this.index=index+_Grade;
            this.starForce = starForce;
        }
        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        public int getStarForce(){
            return starForce;
        }
    }

    public static CardType getCardTypeByCode(String code){
        CardType[] values = CardType.values();
        for(int i=0;i<values.length;i++){
            if(values[i].getCode().equals(code)){
                return values[i];
            }
        }
        return CardType.DEFAULT;
    }
    public  enum CardType{
        DEFAULT("默认","DEFAULT"),
        CHANGDI("场地卡","CHANGDI"),//场地卡
        YINMOU("阴谋卡","YINMOU"),
        XIAOGUO("效果卡","XIAOGUO"),
        ZHUANSHU("专属卡","ZHUANSHU");

        //成员变量
        private String name;
        private String code;
        //构造方法
        private CardType(String name,String code)
        {
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }

    public static Race getRaceByCode(int code){
        Race[] values = Race.values();
        for(int i=0;i<values.length;i++){
            if(values[i].getIndex() == code){
                return values[i];
            }
        }
        return null;
    }
    public enum Race{
        DEFAULT("默认",1);
        private String name;
        private int index;
        private Race(String name,int code)
        {
            this.name=name;
            this.index=code;
        }
        public String getName() {
            return name;
        }
        public int getIndex() {
            return index;
        }
    }
}
