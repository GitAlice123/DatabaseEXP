package DAO;
import java.sql.*;
/*
    * @Description: 商品DAO，获取商品信息
 */
public class ProductDAO {
    public static String[][] getProducts() {
        Connection con;
        Statement st;
        ResultSet rs;
        String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
        String userName="sa";
        String userPwd="yc030316";
        String sql="select * from Products";
        String[][] products = new String[100][4];
        int i = 0;
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                products[i][0] = rs.getString(1);
                products[i][1] = rs.getString(2);
                products[i][2] = rs.getString(3);
                products[i][3] = rs.getString(4);
                i++;
            }
            rs.close();
            st.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
        return products;
    }
    public static String[][] getProducts(String ProductID) {
        Connection con;
        Statement st;
        ResultSet rs;
        String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
        String userName="sa";
        String userPwd="yc030316";
        String sql="select * from Products where ProductID = '" + ProductID + "'";
        String[][] products = new String[100][4];
        int i = 0;
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                products[i][0] = rs.getString(1);
                products[i][1] = rs.getString(2);
                products[i][2] = rs.getString(3);
                products[i][3] = rs.getString(4);
                i++;
            }
            rs.close();
            st.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
        return products;
    }
}
