package com.twtstudio.coder.saishi_android.ui.main;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;
        import java.util.List;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.HttpStatus;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;
        import org.apache.http.params.HttpParams;

        import android.util.Log;

public class LandServer {
    private String url = "http://192.168.1.104:8080/login/SchoolHelper";
    private String result  ;

    public String doPost(String name, String psd) {
        HttpClient hc = new DefaultHttpClient();
        HttpPost hp = new HttpPost(url);
        NameValuePair n = new BasicNameValuePair("name", name);
        NameValuePair u = new BasicNameValuePair("psd", psd);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(n);
        list.add(u);
        try {
            HttpEntity he =new UrlEncodedFormEntity(list,"GBK");
            hp.setEntity(he);
            HttpResponse response = hc.execute(hp);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity het =response.getEntity();
                InputStream in = het.getContent();
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                String redLine = null;
                while((redLine=bf.readLine())!=null){
                    System.out.println("redLine: " + redLine);
                    result =redLine;
                }
                in.close();
            }else{
                result = "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "Exception";
        }
        return result;
    }
}
