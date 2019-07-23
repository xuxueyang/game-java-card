package core.exception;

public class AccessDeniedException extends Exception {
    private String reason;
    private String code;
    public AccessDeniedException(String code,String reason){
        this.code = code;
        this.reason = reason;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
