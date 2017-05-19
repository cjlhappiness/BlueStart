package util;

/*
数据请求地址及错误码转换类
*/

public class Network {

    //数据请求地址
    public static final String FIRST_GET   = "http://cjlhappiness.xicp.net/BlueStart/getFirstJSON.php";
    public static final String FIRST_SET   = "http://cjlhappiness.xicp.net/BlueStart/setFirstJOSN.php";
    public static final String SECOND_GET  = "http://cjlhappiness.xicp.net/BlueStart/getSecondJSON.php";
    public static final String SECOND_SET  = "http://cjlhappiness.xicp.net/BlueStart/getSecondJSON.php";
    public static final String THIRD_GET   = "http://cjlhappiness.xicp.net/BlueStart/getThirdJSON.php";
    public static final String THIRD_SET   = "http://cjlhappiness.xicp.net/BlueStart/getThirdJSON.php";
    public static final String FOURTH_GET  = "http://cjlhappiness.xicp.net/BlueStart/getFourthJSON.php";
    public static final String FOURTH_SET  = "http://cjlhappiness.xicp.net/BlueStart/getFourthJSON.php";
    public static final String FIFTH_GET   = "http://cjlhappiness.xicp.net/BlueStart/getFifthJSON.php";
    public static final String FIFTH_SET   = "http://cjlhappiness.xicp.net/BlueStart/getFifthJSON.php";
    public static final String SIXTH_GET   = "http://cjlhappiness.xicp.net/BlueStart/getSixthJSON.php";
    public static final String SIXTH_SET   = "http://cjlhappiness.xicp.net/BlueStart/getSixthJSON.php";
    public static final String SEVENTH_GET = "http://cjlhappiness.xicp.net/BlueStart/getSeventhJSON.php";
    public static final String SEVENTH_SET = "http://cjlhappiness.xicp.net/BlueStart/getSeventhJSON.php";
    public static final String EIGHTH_GET  = "http://cjlhappiness.xicp.net/BlueStart/getEighthJSON.php";
    public static final String EIGHTH_SET  = "http://cjlhappiness.xicp.net/BlueStart/getEighthJSON.php";
    public static final String NINTH_GET   = "http://cjlhappiness.xicp.net/BlueStart/getNinthJSON.php";
    public static final String NINTH_SET   = "http://cjlhappiness.xicp.net/BlueStart/getNinthJSON.php";

    public static final String LOAD        = "http://cjlhappiness.xicp.net/BlueStart/loadUser.php";

    public static final String UPDATE      = "http://cjlhappiness.xicp.net/BlueStart/BlueStart.apk";

    public static final String HOME        = "http://cjlhappiness.xicp.net";

    public static final String TEST        = "http://www.baidu.com";

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
