package aa.app.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DsClienttest {
    public static void main(String[] args) {
        String message = "123";
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
            conn.getOutputStream().write(message.getBytes("utf8"));
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

            byte[] buffer = new byte[1024];
            String sb = new String ();
            InputStream in = conn.getInputStream();
            int httpCode = conn.getResponseCode();
            System.out.println(in.available());
            while (in.read(buffer) != -1){
                sb+=new String(buffer);
            }
            System.out.println("sb:" + sb);
            in.close();
            System.out.println("返回状态码: " + httpCode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
