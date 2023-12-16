package DAO;
import java.sql.*;
/*
    * @Description: 登录DAO，验证身份以及用户名和密码
 */
public class UserDAO {
    public static boolean login(String username, String password, String identity) {
        Connection con;
        Statement st;
        ResultSet rs;
        String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
        String userName="sa";
        String userPwd="yc030316";
        String sql="select * from ShopUser where Username = '" + username + "' and Password = '" + password + "'"
                + " and Iden = '" + identity + "'";

        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                rs.close();
                st.close();
                con.close();
                return true;
            }
            rs.close();
            st.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
        return false;
    }}
