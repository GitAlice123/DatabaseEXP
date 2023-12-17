package DAO;
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
    static String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
    static String userName = "sa";
    static String userPwd = "yc030316";
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
        String sql = "select OrderItemID from OrderItem";
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        st = con.createStatement();
        rs = st.executeQuery(sql);
        int orderItemNum = 0;
        while (rs.next()) {
            String orderItemID = rs.getString(1);
            // 去除这个String中的末尾空格
            orderItemID = orderItemID.trim();
            orderItemNum = Integer.parseInt(orderItemID.substring(4));
        }
        return orderItemNum;
    }

    // 将订单项信息插入数据库
    public static void insertOrderItem(String orderItemID, String orderID, String productID, String quantity) throws SQLException {
        String sql = "insert into OrderItem values(?, ?, ?, ?, ?)";
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
}
