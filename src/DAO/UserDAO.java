package DAO;
import java.sql.*;
/*
    * @Description: 登录DAO，验证身份以及用户名和密码
 */
public class UserDAO {
    static String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
    static String userName="sa";
    static String userPwd="yc030316";
    public static String login(String username, String password, String identity) {
        Connection con;
        Statement st;
        ResultSet rs;
        String sql="select UserID from ShopUser where Username = '" + username + "' and Password = '" + password + "'"
                + " and Iden = '" + identity + "'";

        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String userID = rs.getString(1);
                rs.close();
                st.close();
                con.close();
                return userID;
            }
            rs.close();
            st.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
        return null;
    }}
