package Global;

public class Global {
    // 存放全局量
    String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
    String userName="sa";
    String userPwd="yc030316";
    public static String userID="00000001";
    // set，get方法
    public static String getUserID() {
        return userID;
    }
    public static void setUserID(String userID) {
        Global.userID = userID;
    }
}
