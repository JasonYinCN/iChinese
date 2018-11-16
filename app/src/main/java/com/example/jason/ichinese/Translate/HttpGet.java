package com.example.jason.ichinese.Translate;

import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpGet {
    protected static final int SOCKET_TIMEOUT = 10000; // 10S
    protected static final String GET = "GET";

    public static String get(String host, Map<String, String> params) {
        try {
            // 设置SSLContext
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

            String sendUrl = getUrlWithQueryString(host, params);

            // System.out.println("URL:" + sendUrl);

            URL uri = new URL(sendUrl); // 创建URL对象
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslcontext.getSocketFactory());
            }

            conn.setConnectTimeout(SOCKET_TIMEOUT); // 设置相应超时
            conn.setRequestMethod(GET);
            int statusCode = conn.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Http错误码：" + statusCode);
            }

            // 读取服务器的数据
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            String text = builder.toString();

            close(br); // 关闭数据流
            close(is); // 关闭数据流
            conn.disconnect(); // 断开连接

            return text;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String get(String sendUrl) {
        try {
            // 设置SSLContext
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

            // System.out.println("URL:" + sendUrl);

            URL uri = new URL(sendUrl); // 创建URL对象
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslcontext.getSocketFactory());
            }

            conn.setConnectTimeout(SOCKET_TIMEOUT); // 设置相应超时
            conn.setRequestMethod(GET);
            int statusCode = conn.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Http错误码：" + statusCode);
            }

            // 读取服务器的数据
            InputStream is = conn.getInputStream();
            JsonReader jsReader = new JsonReader(new InputStreamReader(is));
            try {
                /*开始解析json为object对象*/
                jsReader.beginObject();
                while(jsReader.hasNext()){
                    String tagName = jsReader.nextName();
                    switch (tagName) {
                        case "word_name":
                            System.out.println("name:" + jsReader.nextString());
                            break;
                        case "symbols":
                            readSymbol(jsReader);
                            break;
                        default:
                            //跳过当前值
                            jsReader.skipValue();
                            System.out.println("skip======>");
                            break;
                    }
                }
                jsReader.endObject();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            close(jsReader); // 关闭数据流
            close(is); // 关闭数据流
            conn.disconnect(); // 断开连接

            return "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    //由于读取symbol中的数据
    private static void readSymbol(JsonReader jsReader) throws IOException{
        jsReader.beginArray();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "word_symbol":
                        String word_symbol = jsReader.nextString();
                        System.out.println("word_symbol:" + word_symbol);
                        break;
                    case "symbol_mp3":
                        String symbol_mp3 = jsReader.nextString();
                        System.out.println("symbol_mp3:" + symbol_mp3);
                        break;
                    case "parts":
                        readParts(jsReader);
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            jsReader.endObject();
        }
        jsReader.endArray();
    }

    //由于读取symbol中的数据
    private static void readParts(JsonReader jsReader) throws IOException{
        jsReader.beginArray();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "part_name":
                        String part_name = jsReader.nextString();
                        System.out.println("part_name:" + part_name);
                        break;
                    case "means":
                        readMeans(jsReader);
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            jsReader.endObject();
        }
        jsReader.endArray();
    }

    //由于读取symbol中的数据
    private static void readMeans(JsonReader jsReader) throws IOException{
        jsReader.beginArray();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                if (tagName.equals("word_mean")) {
                    String word_mean = jsReader.nextString();
                    System.out.println("word_mean:"+ word_mean);
                }else {
                    //跳过当前值
                    jsReader.skipValue();
                    System.out.println("skip======>");
                }
            }
            jsReader.endObject();
        }
        jsReader.endArray();
    }

    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }

    protected static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式
     * 
     * @param input 原文
     * @return URL编码. 如果编码失败, 则返回原文
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };
}
