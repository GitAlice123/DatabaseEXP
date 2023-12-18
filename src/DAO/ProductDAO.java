package DAO;
import java.sql.*;
import java.util.Vector;
/*
    * @Description: 商品DAO，获取商品信息
 */
public class ProductDAO {
    static String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
    static String userName="sa";
    static String userPwd="yc030316";
    public static String[][] getProducts() {
        Connection con;
        Statement st;
        ResultSet rs;

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
    public static String[][] getProducts(Vector<String> buyIDlist) {
        if(buyIDlist.isEmpty()){
            System.out.println("购物车为空！！！");
            return null;
        }
        Connection con;
        Statement st;
        ResultSet rs;
        String sql="select * from Products where ProductID IN (";
        // 动态添加每个ID值
        for (int i = 0; i < buyIDlist.size(); i++) {
            sql += "?";  // 使用占位符
            if (i < buyIDlist.size() - 1) {
                sql += ", ";
            }
        }
        sql += ")";

        String[][] products = new String[100][4];
        int i = 0;
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // 设置每个占位符的值为 buyIDlist 中的对应元素
            for (int j = 0; j < buyIDlist.size(); j++) {
                preparedStatement.setString(j + 1, buyIDlist.get(j));
            }

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                products[i][0] = rs.getString(1);
                products[i][1] = rs.getString(2);
                products[i][2] = rs.getString(3);
                products[i][3] = rs.getString(4);
                i++;
            }
            rs.close();
            con.close();
            preparedStatement.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
        return products;
    }
    // updateProduct(productName, productPrice, productStock)
    public static void updateProduct(String productID, String productName, String productPrice, String productStock) {
        Connection con;
        Statement st;
        ResultSet rs;
        String sql="update Products set ProductName = '" + productName + "', Price = " + productPrice + ", StockQuantity = " + productStock + " where ProductID = '" + productID + "'";
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
    }
    // insertProduct(productID)
    public static void insertProduct(String productID, String productName, String productPrice, String productStock) {
        Connection con;
        Statement st;
        ResultSet rs;
        String sql="insert into Products values('" + productID + "', '" + productName + "', " + productPrice + ", " + productStock + ")";
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }
    }
}
