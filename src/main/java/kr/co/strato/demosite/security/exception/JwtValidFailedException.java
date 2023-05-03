package kr.co.strato.demosite.security.exception;

public class JwtValidFailedException extends RuntimeException {
    public JwtValidFailedException() {
        super("Failed to generate Token");
    }

    public JwtValidFailedException(String message) {
        super(message);
    }
}
