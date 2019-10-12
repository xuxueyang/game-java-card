package netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.core.RequestDTO;
import core.dto.file.*;
import core.protocol.Protocol;
import netty.handler.inter.AbstactSelfServerHandler;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import netty.proto.MsgUtil;


;import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileServerHandler extends AbstactSelfServerHandler<RequestDTO, RequestDTO> {
    private static Log log = LogFactory.getLog(FileServerHandler.class);

    private core.dto.file.CacheUtil cacheUtil = new core.dto.file.CacheUtil();
    @Override
    public void channelRead(Channel ctx, RequestDTO dto) throws Exception {
        Object data = dto.getData();
        //数据格式验证
        FileTransferProtocol fileTransferProtocol = null;
        if(data!=null && data instanceof JSONObject){
            fileTransferProtocol = (FileTransferProtocol)JSON.parseObject(JSON.toJSONString(data),FileTransferProtocol.class);
        }else if (!(data instanceof FileTransferProtocol)) {
            log.error("数据格式不对");
            return;
        };
        if(fileTransferProtocol.getTransferType()==null){
            log.error("数据格式不对:fileTransferProtocol.getTransferType()==null");
            return;
        }
        //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        switch (fileTransferProtocol.getTransferType()) {
            case 0:
                FileDescInfo fileDescInfo = (FileDescInfo) MsgUtil.parseObject(fileTransferProtocol.getTransferObj(),FileDescInfo.class);

                //断点续传信息，实际应用中需要将断点续传信息保存到数据库中
                FileBurstInstruct fileBurstInstructOld = cacheUtil.burstDataMap.get(fileDescInfo.getFileName());
                if (null != fileBurstInstructOld) {
                    if (fileBurstInstructOld.getStatus() == FileConstants.FileStatus.COMPLETE) {
                        cacheUtil.burstDataMap.remove(fileDescInfo.getFileName());
                    }
                    //传输完成删除断点信息
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 服务端，接收客户端传输文件请求[断点续传]。" + JSON.toJSONString(fileBurstInstructOld));
                    ctx.sendMsg(MsgUtil.buildObj(FileUtil.buildTransferInstruct(fileBurstInstructOld), Protocol.Type.FILE));
                    return;
                }

                //发送信息
                FileTransferProtocol sendFileTransferProtocol = FileUtil.buildTransferInstruct(FileConstants.FileStatus.BEGIN, fileDescInfo.getFileUrl(), 0);
                ctx.sendMsg(MsgUtil.buildObj(sendFileTransferProtocol,Protocol.Type.FILE));
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 服务端，接收客户端传输文件请求。" + JSON.toJSONString(fileDescInfo));
                break;
            case 2:
                FileBurstData fileBurstData = (FileBurstData) MsgUtil.parseObject(fileTransferProtocol.getTransferObj(),FileBurstData.class);

                FileBurstInstruct fileBurstInstruct = FileUtil.writeFile("D://", fileBurstData);

                //保存断点续传信息
                cacheUtil.burstDataMap.put(fileBurstData.getFileName(), fileBurstInstruct);

                ctx.sendMsg(MsgUtil.buildObj(FileUtil.buildTransferInstruct(fileBurstInstruct),Protocol.Type.FILE));
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 栈服务端，接收客户端传输文件数据。" + JSON.toJSONString(fileBurstData));

                //传输完成删除断点信息
                if (fileBurstInstruct.getStatus() == FileConstants.FileStatus.COMPLETE) {
                    cacheUtil.burstDataMap.remove(fileBurstData.getFileName());
                }
                break;
            default:
                break;
        }


    }

    @Override
    public byte getType() {
         return Protocol.Type.FILE;
    }

    @Override
    public void init() {

    }


}
