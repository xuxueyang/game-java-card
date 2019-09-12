package core.dto.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtil {
    public static FileBurstData readFile(String fileUrl, Integer readPosition) throws IOException {
        File file = new File(fileUrl);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");//r: 只读模式 rw:读写模式
        randomAccessFile.seek(readPosition);
        byte[] bytes = new byte[1024];
        int readSize = randomAccessFile.read(bytes);
        if (readSize <= 0) {
            randomAccessFile.close();
            return new FileBurstData(FileConstants.FileStatus.COMPLETE);//Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        }
        FileBurstData fileInfo = new FileBurstData();
        fileInfo.setFileUrl(fileUrl);
        fileInfo.setFileName(file.getName());
        fileInfo.setBeginPos(readPosition);
        fileInfo.setEndPos(readPosition + readSize);
        //不足1024需要拷贝去掉空字节
        if (readSize < 1024) {
            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);
            fileInfo.setBytes(copy);
            fileInfo.setStatus(FileConstants.FileStatus.END);
        } else {
            fileInfo.setBytes(bytes);
            fileInfo.setStatus(FileConstants.FileStatus.CENTER);
        }
        randomAccessFile.close();
        return fileInfo;
    }

    public static FileBurstInstruct writeFile(String baseUrl, FileBurstData fileBurstData) throws IOException {

        if (FileConstants.FileStatus.COMPLETE == fileBurstData.getStatus()) {
            return new FileBurstInstruct(FileConstants.FileStatus.COMPLETE); //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        }

        File file = new File(baseUrl + "/" + fileBurstData.getFileName());
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");//r: 只读模式 rw:读写模式
        randomAccessFile.seek(fileBurstData.getBeginPos());      //移动文件记录指针的位置,
        randomAccessFile.write(fileBurstData.getBytes());        //调用了seek（start）方法，是指把文件的记录指针定位到start字节的位置。也就是说程序将从start字节开始写数据
        randomAccessFile.close();

        if (FileConstants.FileStatus.END == fileBurstData.getStatus()) {
            return new FileBurstInstruct(FileConstants.FileStatus.COMPLETE); //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        }

        //文件分片传输指令
        FileBurstInstruct fileBurstInstruct = new FileBurstInstruct();
        fileBurstInstruct.setStatus(FileConstants.FileStatus.CENTER);            //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        fileBurstInstruct.setClientFileUrl(fileBurstData.getFileUrl());      //客户端文件URL
        fileBurstInstruct.setReadPosition(fileBurstData.getEndPos() + 1);    //读取位置

        return fileBurstInstruct;
    }


    /**
     * 构建对象；请求传输文件(客户端)
     *
     * @param fileUrl  客户端文件地址
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @return 传输协议
     */
    public static FileTransferProtocol buildRequestTransferFile(String fileUrl, String fileName, Long fileSize) {

        FileDescInfo fileDescInfo = new FileDescInfo();
        fileDescInfo.setFileUrl(fileUrl);
        fileDescInfo.setFileName(fileName);
        fileDescInfo.setFileSize(fileSize);

        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(FileConstants.TransferType.BEGIN);//0请求传输文件、1文件传输指令、2文件传输数据
        fileTransferProtocol.setTransferObj(fileDescInfo);

        return fileTransferProtocol;

    }

    /**
     * 构建对象；文件传输指令(服务端)
     * @param status          0请求传输文件、1文件传输指令、2文件传输数据
     * @param clientFileUrl   客户端文件地址
     * @param readPosition    读取位置
     * @return                传输协议
     */
    public static FileTransferProtocol buildTransferInstruct(Integer status, String clientFileUrl, Integer readPosition) {

        FileBurstInstruct fileBurstInstruct = new FileBurstInstruct();
        fileBurstInstruct.setStatus(status);
        fileBurstInstruct.setClientFileUrl(clientFileUrl);
        fileBurstInstruct.setReadPosition(readPosition);

        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(FileConstants.TransferType.INSTRUCT); //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        fileTransferProtocol.setTransferObj(fileBurstInstruct);

        return fileTransferProtocol;
    }

    /**
     * 构建对象；文件传输指令(服务端)
     *
     * @return 传输协议
     */
    public static FileTransferProtocol buildTransferInstruct(FileBurstInstruct fileBurstInstruct) {
        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(FileConstants.TransferType.INSTRUCT);  //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        fileTransferProtocol.setTransferObj(fileBurstInstruct);
        return fileTransferProtocol;
    }

    /**
     * 构建对象；文件传输数据(客户端)
     *
     * @return 传输协议
     */
    public static FileTransferProtocol buildTransferData(FileBurstData fileBurstData) {
        FileTransferProtocol fileTransferProtocol = new FileTransferProtocol();
        fileTransferProtocol.setTransferType(FileConstants.TransferType.DATA); //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        fileTransferProtocol.setTransferObj(fileBurstData);
        return fileTransferProtocol;
    }
}
