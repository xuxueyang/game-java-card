package dist;

public class DIST {
    private static int Attribute = 1000;

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
        private Attribute(String name,int index)
        {
            this.name=name;
            this.index=index+Attribute;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
    private static int Pinji = 2000;
    public enum Pinji{
        R("R",1),
        S("S",2),
        SS("SS",3),
        SSR("SSR",4);


        //成员变量
        private String name;
        private int index;
        //构造方法
        private Pinji(String name,int index)
        {
            this.name=name;
            this.index=index+Pinji;
        }
        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
}
