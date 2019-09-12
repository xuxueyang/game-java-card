package core.dto.file;

public class FileConstants {
    public static  class   FileStatus {
        public static final int COMPLETE = 3;
        public static final int END = 2;
        public static final int CENTER = 1;
        public static final int BEGIN = 0;
    }
//    FileConstants.TransferType.INSTRUCT); //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
    public static  class   TransferType {
        public static final int DATA = 2;
        public static final int INSTRUCT = 1;
        public static final int BEGIN = 0;
    }
}
