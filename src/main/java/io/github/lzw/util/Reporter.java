package io.github.lzw.util;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * report
 */
public class Reporter {

    private final static Reporter REPORTER = new Reporter();
    private Retrofit retrofit;

    private Reporter() {
        if (REPORTER != null) {
            throw new RuntimeException();
        }
        retrofit = new Retrofit.Builder().baseUrl("https://qyapi.weixin.qq.com/")
                .addConverterFactory(FastJsonConverterFactory.create()).build();
    }

    public static Reporter get() {
        return REPORTER;
    }

    public void send(String msg) {
        try {
            String body = retrofit.create(WXService.class).send(new Message(new Text(msg))).execute().body().string();
            System.out.println(body);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Innerreport
     */
    public interface WXService {
        @Headers({ "Content-Type: application/json" })
        @POST("cgi-bin/webhook/send?key=a5c8f891-86e7-4888-93d7-42a4187dd392")
        Call<ResponseBody> send(@Body Message msg);
    }

    /**
     * InnerReporter
     */
    public class Message {
        private String msgtype;
        private Text text;

        /**
         * InnerReporter
         */

        public Message(Text text) {
            msgtype = "text";
            this.text = text;
        }

        public String getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(String msgtype) {
            this.msgtype = msgtype;
        }

        public Text getText() {
            return text;
        }

        public void setText(Text text) {
            this.text = text;
        }
    }

    public static class Text {
        private String content;

        public Text(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }
}