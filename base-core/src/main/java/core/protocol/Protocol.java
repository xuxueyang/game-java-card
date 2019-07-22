package core.protocol;

public  interface Protocol {
    public static String TOKEN = "TOKEN";
    public static class Type{
        public static Byte LOGIN = 0;
        public static Byte CHAT = 1;
        public static Byte ITEM = 2;
    }
}
