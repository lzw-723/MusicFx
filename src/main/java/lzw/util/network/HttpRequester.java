package lzw.util.network;

import lzw.util.HashUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpRequester {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static boolean allowCache = false;
    private static HashMap<String, String> Cache;

    public static void get(String url, Map<String, String> request, Map<String, String> head, Listener l, boolean async) {
        sendRequest(url, request, GET, head, l, async);
    }

    public static void post(String url, Map<String, String> request, Map<String, String> head, Listener l, boolean async) {
        sendRequest(url, request, POST, head, l, async);
    }

    public static void sendRequest(final String url, final Map<String, String> request, final String requestMethod, final Map<String, String> head, final Listener l, boolean async) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String strurl = url;
                    String requestStr = "";
                    if (request != null) {
                        Iterator<String> it1 = request.keySet().iterator();
                        while (it1.hasNext()) {
                            String key = it1.next();
                            String value = request.get(key);
                            requestStr += key + "=" + value;
                            if (it1.hasNext())
                                requestStr += "&";
                        }
                    }
                    switch (requestMethod) {
                        case "GET":
                            strurl = url + "?" + requestStr;
                            break;
                    }
                    String hash = HashUtil.md5(strurl + requestStr + requestMethod);
                    HttpURLConnection a = null;
                    URL u = new URL(strurl);
                    a = (HttpURLConnection) u.openConnection();
                    a.setReadTimeout(8000);
                    a.setConnectTimeout(8000);
                    a.setRequestMethod(requestMethod);
                    a.setDoInput(true);
                    if (head != null) {
                        Iterator<String> it = head.keySet().iterator();
                        while (it.hasNext()) {
                            String key = it.next();
                            String value = head.get(key);
                            a.addRequestProperty(key, value);
                        }
                    }
                    switch (requestMethod) {
                        case POST:
                            a.setDoOutput(true);
                            OutputStream os = a.getOutputStream();
                            new DataOutputStream(os).write(requestStr.getBytes());
                            break;
                    }
                    InputStream input = a.getInputStream();
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int n = 0;
                    while (-1 != (n = input.read(buffer))) {
                        output.write(buffer, 0, n);
                    }
                    if (l != null) {
                        l.onRequested(a);
                        l.onSuccess(output.toByteArray());
                    }
                } catch (Exception e) {
                    if (l != null)
                        l.onFail(e);
                } finally {
                }
            }
        };
        if (async) new Thread(runnable).start();
        else runnable.run();
    }

    public static abstract class Listener {
        public abstract void onSuccess(byte[] result);

        public abstract void onFail(Exception e);

        public void onRequested(HttpURLConnection httpURLConnection) {
        }
    }

    public static class Builder {
        private HashMap<String, String> head = new HashMap<>();
        private HashMap<String, String> request = new HashMap<>();
        private String url = "";
        private boolean async = false;

        public Builder() {
        }

        public Builder setAsync(boolean async) {
            this.async = async;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setUserAgent(String ua) {
            head.put("User-Agent", ua);
            return this;
        }

        public Builder setReferer(String referer) {
            head.put("Referer", referer);
            return this;
        }

        public Builder setCookie(String cookie) {
            head.put("Cookie", cookie);
            return this;
        }

        public Builder setCache(boolean isAllow) {
            HttpRequester.allowCache = isAllow;
            return this;
        }

        public Builder addData(String name, String data) {
            request.put(name, data);
            return this;
        }

        public Builder addHead(String name, String value) {
            head.put(name, value);
            return this;
        }

        public void get() {
            get(null);
        }

        public void post() {
            post(null);
        }

        public void get(Listener l) {
            HttpRequester.get(url, request, head, l, async);
        }

        public void post(Listener l) {
            HttpRequester.post(url, request, head, l, async);
        }
    }

}
