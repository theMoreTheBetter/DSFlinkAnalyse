package aa.app.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DsClienttest {
    //TODO
    public static void main(String[] args) {
        String message = "学习项目整合";
        String address = "http://localhost:6097/dsInfoController/webInfoController";
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(true);
            conn.setUseCaches(false);
            conn.setReadTimeout(6*1000);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
            conn.setRequestProperty("Content-Type","application/json");
            conn.connect();
            OutputStream outputStream = conn.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            byte[] mbytes = message.getBytes();
            out.write(mbytes);
            out.flush();
            out.close();

            String temp="";
            InputStream in = conn.getInputStream();
            byte[] tempbytes = new byte[8];
            while(in.read(tempbytes) != -1){
                temp = new String(tempbytes);
            }
            System.out.println(conn.getResponseCode());
            System.out.println(temp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
