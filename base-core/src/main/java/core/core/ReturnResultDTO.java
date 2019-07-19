package core.core;

public class ReturnResultDTO<T> implements java.io.Serializable {

    private String returnCode;

    private T data;

    public ReturnResultDTO() {
    }

    public ReturnResultDTO(String returnCode, T data) {
        this.setData(data);
        this.setReturnCode(returnCode);
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}