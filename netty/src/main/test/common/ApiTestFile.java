package common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import netty.config.DefaultChannelInitializer;
import netty.config.DefaultFrameDecoder;
import netty.config.DefaultFrameEncoder;
import core.core.RequestDTO;
import core.dto.file.*;
import core.protocol.Protocol;
import core.util.MD5Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import netty.proto.MsgUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ApiTestFile {
    public static void checkFile(String file1,String file2) throws IOException {
        File one1 = new File(file1);
        File two2 = new File(file2);
        RandomAccessFile one = new RandomAccessFile(one1, "r");//r: 只读模式 rw:读写模式
        RandomAccessFile two = new RandomAccessFile(two2, "r");//r: 只读模式 rw:读写模式
        Integer position = 0;
        while (true){
            one.seek(position);
            two.seek(position);
            byte[] bytes = new byte[1024];
            byte[] bytes2 = new byte[1024];
            int readSize = one.read(bytes);
            int readSize2 = two.read(bytes2);
            if(readSize>0){
                byte[] copy = new byte[readSize];
                byte[] copy2 = new byte[readSize2];
                System.arraycopy(bytes, 0, copy, 0, readSize);
                System.arraycopy(bytes, 0, copy2, 0, readSize2);
//                System.out.println("————————————————————————————");
                String x = JSON.toJSONString(copy);
//                System.out.println(x);
                String x1 = JSON.toJSONString(copy2);
//                System.out.println(x1);
                if(!x.equals(x1)){
                    System.out.println("————————————————————————————");
                }
//                System.out.println("————————————————————————————");

                if(readSize<1024){
                    return;
                }else {
                    position+=1024;

                }
            }else{
                return;
            }
        }
    }
    public static void main(String[] args) {
        testFile("D:\\project_new\\protoc-3.9.1-win64.zip");
//        testFile("D:\\project_new\\game-java-card\\managerroom\\src\\main\\java\\roommanager\\RoomApplication.java");
//        try {
//            checkFile("D:\\project_new\\protoc-3.9.1-win64.zip","D:\\protoc-3.9.1-win64.zip");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    private static void testFile(String filePath){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    if(DefaultChannelInitializer.useProtobuf){
                        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        channel.pipeline().addLast(new ProtobufDecoder(netty.proto.dto.RequestDTO.RequestDTOProto.getDefaultInstance()));
                        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        channel.pipeline().addLast(new ProtobufEncoder());
                    }else{
                        channel.pipeline().addLast(new DefaultFrameDecoder());
                        channel.pipeline().addLast(new DefaultFrameEncoder());
                        // 解码转String，注意调整自己的编码格式GBK、UTF-8
                        channel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                        // 解码转String，注意调整自己的编码格式GBK、UTF-8
                        channel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                    }

                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object dto) throws Exception {
                            //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
                            RequestDTO tmp = null;
                            if(DefaultChannelInitializer.useProtobuf){
                                tmp = (RequestDTO)JSON.parseObject(((netty.proto.dto.RequestDTO.RequestDTOProto) dto).getMessage(),RequestDTO.class);
                            }else {
                                tmp = (RequestDTO)dto;
                            }

                            //数据格式验证
                            Object msg = tmp.getData();
                            if(msg!=null && msg instanceof JSONObject){
                                msg = MsgUtil.parseObject(tmp.getData(),FileTransferProtocol.class);
                            }else  if (!(msg instanceof FileTransferProtocol)) {
                                System.out.println("error : msg instanceof FileTransferProtocol");
                                return;
                            };

                            FileTransferProtocol fileTransferProtocol = (FileTransferProtocol) msg;
                            //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
                            switch (fileTransferProtocol.getTransferType()) {
                                case 1:
                                    FileBurstInstruct fileBurstInstruct = (FileBurstInstruct) MsgUtil.parseObject(fileTransferProtocol.getTransferObj(),FileBurstInstruct.class);
//                                    FileBurstInstruct fileBurstInstruct = (FileBurstInstruct) fileTransferProtocol.getTransferObj();
                                    //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
                                    if (FileConstants.FileStatus.COMPLETE == fileBurstInstruct.getStatus()) {
                                        ctx.flush();
                                        ctx.close();
                                        System.exit(-1);
                                        return;
                                    }
                                    FileBurstData fileBurstData = null;
                                    try {
                                         fileBurstData = FileUtil.readFile(fileBurstInstruct.getClientFileUrl(), fileBurstInstruct.getReadPosition());
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    FileTransferProtocol clientSend = new FileTransferProtocol();
                                    clientSend.setTransferType(FileConstants.TransferType.DATA);//0请求传输文件、1文件传输指令、2文件传输数据
                                    clientSend.setTransferObj(fileBurstData);

                                    ctx.channel().writeAndFlush(MsgUtil.buildObj(clientSend,Protocol.Type.FILE));
                                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 客户端传输文件信息。 FILE：" + fileBurstData.getFileName() + " SIZE(byte)：" + (fileBurstData.getEndPos() - fileBurstData.getBeginPos()));
                                    break;
                                default:
                                    break;
                            }

                            /**模拟传输过程中断，场景测试可以注释掉
                             *
                             */
//                            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 客户端传输文件信息[主动断开链接，模拟断点续传]");
//                            ctx.flush();
//                            ctx.close();
//                            System.exit(-1);
                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            System.out.println("itstack-demo-netty client start done.");

            //向服务端发送信息
            RequestDTO dto = new RequestDTO();
            HashMap<Object, Object> data = new HashMap<>();
            data.put("message1","Hello world");
            dto.setUserId(1L);
            dto.setTimestamp(new Date().getTime());
            dto.setArea(Protocol.Area.Netty);
            dto.setAreaL(1L);
            dto.setType(Protocol.Type.LOGIN);
            dto.setMd5(MD5Util.MD5("8254a4f9aa08420092f3c6f8f01b2370" + dto.getTimestamp() ));
            dto.setData(data);

            f.channel().writeAndFlush(MsgUtil.sendObj(dto));

            RequestDTO dto2 = new RequestDTO();
            dto2.setUserId(1L);
            dto2.setTimestamp(new Date().getTime());
            dto2.setArea(Protocol.Area.Netty);
            dto2.setAreaL(1L);
            dto2.setMd5(MD5Util.MD5("8254a4f9aa08420092f3c6f8f01b2370" + dto.getTimestamp() ));

            dto2.setType(Protocol.Type.FILE);


            //传输文件
            //文件信息{文件大于1024kb方便测试断点续传}
            File file = new File(filePath);
            FileTransferProtocol fileTransferProtocol = FileUtil.buildRequestTransferFile(file.getAbsolutePath(), file.getName(), file.length());

            dto2.setData(fileTransferProtocol);

            f.channel().writeAndFlush(MsgUtil.sendObj(dto2));


            f.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
