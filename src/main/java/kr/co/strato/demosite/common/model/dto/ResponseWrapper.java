package kr.co.strato.demosite.common.model.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResponseWrapper<T> implements Serializable {
    private Header  header;
    private T       body;

    public ResponseWrapper() {
        header = new Header();
        this.header.setResultCode(HttpStatus.OK.value());
        this.header.setResultMsg(HttpStatus.OK.getReasonPhrase());
    }

    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }
    public T getBody() {
        return body;
    }
    public void setBody(T body) {
        this.body = body;
    }

    public class Header {
        private int     resultCode;
        private String  resultMsg;
        private String  extra;

        public int getResultCode() {
            return resultCode;
        }
        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
        public String getResultMsg() {
            return resultMsg;
        }
        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
        public String getExtra() {
            return extra;
        }
        public void setExtra(String extra) {
            this.extra = extra;
        }
    }
}
