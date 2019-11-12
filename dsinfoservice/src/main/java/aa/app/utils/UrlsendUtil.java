package aa.app.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlsendUtil {
    public static String sendmessage(String address,String message){
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
            return temp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
