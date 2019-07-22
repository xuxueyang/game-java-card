package core.exception;

public class AccessDeniedException extends Exception {
    private String reason;
    private String code;
    public AccessDeniedException(String code,String reason){
        this.code = code;
        this.reason = reason;

    }
}
