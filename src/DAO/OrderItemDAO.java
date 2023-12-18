package DAO;
import Global.Global;

import java.sql.*;


/*
    * @Description: 订单项DAO，订单项信息
    * 包括以下字段：
    * OrderItemID 订单项编号,格式为OrItem+三位数字，如OrItem001
    * OrderID 订单编号
    * ProductID 商品编号
    * Quantity 购买数量
    * HasPaid 是否已付款
 */
public class OrderItemDAO {
    static String dbURL= Global.getDbURL();
    static String userName=Global.getUserName();
    static String userPwd=Global.getUserPwd();
    static Connection con;
    static Statement st;
    static ResultSet rs;

    // 生成订单项
    public static void generateOrderItem(String orderID, String productID, String quantity) throws SQLException {
        // 生成订单项编号
        String orderItemID = generateOrderItemID();
        // 将订单项信息插入数据库
        insertOrderItem(orderItemID, orderID, productID, quantity);
    }

    // 生成订单项编号
    public static String generateOrderItemID() throws SQLException {
        // 从数据库中获取当前订单项数
        int orderItemNum = getOrderItemNum();
        // 生成订单项编号
        return "OrItem" + String.format("%03d", orderItemNum + 1);
    }

    // 从数据库中获取当前订单项数
    public static int getOrderItemNum() throws SQLException {
        // 从数据库中获取当前订单项编号的最后一个数字
        String sql = "select OrderItemID from OrderItems";
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        st = con.createStatement();
        rs = st.executeQuery(sql);
        int orderItemNum = 0;
        while (rs.next()) {
            String orderItemID = rs.getString(1);
            // 去除这个String中的末尾空格
            orderItemID = orderItemID.trim();
            orderItemNum = Integer.parseInt(orderItemID.substring(6));
        }
        return orderItemNum;
    }

    // 将订单项信息插入数据库
    public static void insertOrderItem(String orderItemID, String orderID, String productID, String quantity) throws SQLException {
        String sql = "insert into OrderItems values(?, ?, ?, ?, ?)";
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, orderItemID);
        ps.setString(2, orderID);
        ps.setString(3, productID);
        ps.setString(4, quantity);
        ps.setString(5, "0");
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    // 获取订单项信息
    public static String[][] getPaidItemsInfo() {
        // 获取已经支付的订单项信息：商品编号，商品名称，购买数量
        String sql = "SELECT OrderItems.ProductID, MAX(ProductName) AS ProductName, SUM(Quantity) AS TotalQuantity " +
                "FROM OrderItems, Products " +
                "WHERE OrderItems.ProductID = Products.ProductID AND HasPaid = 1 " +
                "GROUP BY OrderItems.ProductID";
        String[][] itemsInfo = new String[100][3];
        int i = 0;
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                itemsInfo[i][0] = rs.getString(1);
                itemsInfo[i][1] = rs.getString(2);
                itemsInfo[i][2] = rs.getString(3);
                i++;
            }
            rs.close();
            con.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemsInfo;
    }

    // 获取某年度销售额信息
    // sql查询语句为：SELECT SUM(Quantity*Price) AS TotalRev
    //FROM OrderItems,Products
    //WHERE OrderID IN(
    //	SELECT OrderID
    //	FROM Payment
    //	WHERE YEAR(PaymentTime)=2023
    //) AND OrderItems.ProductID = Products.ProductID

    public static String getYearlyRevenue(String year) {
        String sql = "SELECT SUM(Quantity*Price) AS TotalRev " +
                "FROM OrderItems,Products " +
                "WHERE OrderID IN( " +
                "	SELECT OrderID " +
                "	FROM Payment " +
                "	WHERE YEAR(PaymentTime)=" + year +
                ") AND OrderItems.ProductID = Products.ProductID";
        String revenue = "";
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                revenue = rs.getString(1);
            }
            rs.close();
            con.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return revenue;
    }

    public static String[][] getAllItemsInfo() {
        // 获取所有信息：订单项编号，订单编号，商品编号，购买数量，是否已付款
        String sql = "SELECT * FROM OrderItems";
        String[][] itemsInfo = new String[100][5];
        int i = 0;
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                itemsInfo[i][0] = rs.getString(1);
                itemsInfo[i][1] = rs.getString(2);
                itemsInfo[i][2] = rs.getString(3);
                itemsInfo[i][3] = rs.getString(4);
                itemsInfo[i][4] = rs.getString(5);
                i++;
            }
            rs.close();
            con.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemsInfo;
    }

    public static String[][] specialQuery(){
        // 查询购买了不少于5个库存小于50个的商品的用户的支付时间在2023年6月20日之前的所有订单内容
        String sql = "SELECT * FROM OrderItems " +
                "WHERE OrderID IN(" +
                "SELECT OrderID FROM Orders " +
                "WHERE UserID IN( " +
                "SELECT UserID " +
                "FROM Orders " +
                "WHERE OrderID IN( " +
                "SELECT OrderID FROM OrderItems " +
                "WHERE ProductID IN( " +
                "SELECT ProductID FROM Products " +
                "WHERE StockQuantity<50 ) " +
                "AND Quantity>=5))" +
                "AND OrderTime<='2023-06-20 00:00:00')";
        String[][] itemsInfo = new String[100][5];
        int i = 0;
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                itemsInfo[i][0] = rs.getString(1);
                itemsInfo[i][1] = rs.getString(2);
                itemsInfo[i][2] = rs.getString(3);
                itemsInfo[i][3] = rs.getString(4);
                itemsInfo[i][4] = rs.getString(5);
                i++;
            }
            rs.close();
            con.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemsInfo;
    }

    public static String[] getSpecialUser(){
        // 查询购买了只有一个订单有购买过的商品的客户的名字
//        String sql = "SELECT Username" +
//                "FROM ShopUser" +
//                "WHERE UserID IN(" +
//                "SELECT UserID" +
//                "FROM Orders" +
//                "WHERE OrderID IN" +
//                "(" +
//                "SELECT DISTINCT OrderID" +
//                "FROM OrderItems" +
//                "WHERE ProductID NOT IN (" +
//                "SELECT X.ProductID" +
//                "FROM OrderItems AS X,OrderItems AS Y" +
//                "WHERE X.ProductID=Y.ProductID" +
//                "AND X.OrderID!=Y.OrderID" +
//                ")" +
//                ")" +
//                ")";
        String sql = "SELECT Username\n" +
                "FROM ShopUser\n" +
                "WHERE UserID IN(\n" +
                "\tSELECT UserID\n" +
                "\tFROM Orders\n" +
                "\tWHERE OrderID IN\n" +
                "\t(\n" +
                "\t\tSELECT DISTINCT OrderID\n" +
                "\t\tFROM OrderItems\n" +
                "\t\tWHERE ProductID NOT IN (\n" +
                "\t\tSELECT X.ProductID\n" +
                "\t\tFROM OrderItems AS X,OrderItems AS Y\n" +
                "\t\tWHERE X.ProductID=Y.ProductID\n" +
                "\t\tAND X.OrderID!=Y.OrderID\n" +
                "\t\t)\n" +
                "\t)\n" +
                ")";
        String[] users = new String[100];
        int i = 0;
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                users[i] = rs.getString(1);
                i++;
            }
            rs.close();
            con.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

}
