package util;

public class Network {

    //根据错误码获得错误状态
    public static String getResultState(int code){
        switch (code){
            case 200:
                return "请求成功！";
            case 404:
                return "服务器无法访问！";
            case 405:
                return "服务器不在线！";
            case 999:
                return "服务器映射被关闭！";//自定义的一个错误类型
            default:
                return "未知错误！";
        }
    }


}
