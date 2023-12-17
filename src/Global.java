public class Global {
    // 存放全局量，即用户ID
    public static String userID;
    // set，get方法
    public static String getUserID() {
        return userID;
    }
    public static void setUserID(String userID) {
        Global.userID = userID;
    }
}
