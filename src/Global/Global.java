package Global;

public class Global {
    // 存放全局量
    static String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
    static String userName="sa";
    static String userPwd="eWMwMzAzMTY=";// 使用base64加密后的密码
    public static String getDbURL() {
        return dbURL;
    }
    public static String getUserName() {
        return userName;
    }
    public static String getUserPwd() {
        // 进行base64解密
        byte[] bytes = java.util.Base64.getDecoder().decode(userPwd);
        return new String(bytes);
    }
    public static String userID;
    // set，get方法
    public static String getUserID() {
        return userID;
    }
    public static void setUserID(String userID) {
        Global.userID = userID;
    }
}
