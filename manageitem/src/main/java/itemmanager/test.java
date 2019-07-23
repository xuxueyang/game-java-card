package itemmanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.core.RequestDTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class test {
    public static void main(String[] args){
        try {
            transfer(new RequestDTO(),"http://localhost:20007/rpc/acct/CHECK_LOGIN");
//            URL url = new URL("http://localhost:20007/rpc/acct/TEST");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//
//            connection.getInputStream();
//            String strResponse = connection.getResponseCode()+":"+connection.getResponseMessage();
//            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static String transfer(RequestDTO data, String serverUrl) throws Exception {
        // 服务地址
        URL url;
        try {
            url = new URL(serverUrl);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
//            Util.err(e1);
            return "fail";
        }
        // 设定连接的相关参数
        HttpURLConnection connection = null;
        String strResponse = "";
        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(50 * 1000);// 10秒
            connection.setReadTimeout(50 * 1000);// 10秒
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "text/html; charset=utf-8");
            connection.setRequestProperty("Connection", "close");
            // 输出
            OutputStreamWriter output = new OutputStreamWriter(connection
                    .getOutputStream(),"UTF-8");
            output.write(JSONObject.toJSONString(data));
            output.flush();
            output.close();

            // 获取服务端的反馈
            String strLine = "";
            InputStream in = connection.getInputStream();

            reader = new BufferedReader(
                    new InputStreamReader(in,"UTF-8"));

            while ((strLine = reader.readLine()) != null) {
                strResponse += strLine ;
            }
        } catch (ProtocolException e) {
//            Util.err(e);
            e.printStackTrace();
        } catch (IOException e) {
//            Util.err(e);
            e.printStackTrace();
        } finally{
            try{
                if(reader != null){
                    reader.close();
                }
            }catch(Exception e){e.printStackTrace();}
            try{
                connection.disconnect();
            }catch(Exception e){
//                Util.err(e);
                e.printStackTrace();
            }
        }
        if(strResponse == null || strResponse.equals(""))
            return "fail";
        return strResponse;
    }
}
