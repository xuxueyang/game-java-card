package core.rpc;

public interface AcctRPCConstant {
    String port = "20007";
    String SERVICE_NAME = "acct";

    String CHECK_LOGIN = "/netty.rpc/acct/CHECK_LOGIN";
    String GET_USER_INFO = "/netty.rpc/acct/GET_USER_INFO";
    String GET_TOKEN = "/netty.rpc/acct/GET_TOKEN";
}
