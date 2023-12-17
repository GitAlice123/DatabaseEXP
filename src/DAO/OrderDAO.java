package DAO;

import java.sql.*;

/*
    * @Description: 订单DAO
    * 数据库中有一个订单表，名为Orders，包含以下字段：
    * OrderID：订单编号，主键，自增，格式为Or001，Or002，Or003，……
    * UserID：用户编号，外键，对应ShopUser表中的UserID
    * OrderTime: 下单时间，数据库中的数据类型为datetime
    * TotalMoney: 总金额，数据库中的数据类型为float
    * HasPaid: 是否已付款，数据库中的数据类型为bit
    *
 */
public class OrderDAO {
    static String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
    static String userName="sa";
    static String userPwd="yc030316";
    static Connection con;
    static Statement st;
    static ResultSet rs;
    // 生成订单
    public static String generateOrder(String userID, String totalMoney) throws SQLException {
        // 生成订单编号
        String orderID = generateOrderID();
        // 获取当前时间
        String orderTime = getCurrentTime();
        // 将订单信息插入数据库
        insertOrder(orderID, userID, orderTime, totalMoney);
        return orderID;
    }
    // 生成订单编号
    public static String generateOrderID() throws SQLException {
        // 从数据库中获取当前订单数
        int orderNum = getOrderNum();
        // 生成订单编号
        return "Or" + String.format("%03d", orderNum + 1);
    }
    // 获取当前时间
    public static String getCurrentTime(){
        // 获取当前时间
        java.util.Date date = new java.util.Date();
        // 转换为数据库中的datetime类型
        java.sql.Timestamp currentTime = new java.sql.Timestamp(date.getTime());
        return currentTime.toString();
    }
    // 从数据库中获取当前订单数
    public static int getOrderNum() throws SQLException {
        // 从数据库中获取当前订单编号的最后一个数字
        String sql="select OrderID from Orders";
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        st = con.createStatement();
        rs = st.executeQuery(sql);
        int orderNum = 0;
        while (rs.next()) {
            String orderID = rs.getString(1);
            // 去除这个String中的末尾空格
            orderID = orderID.trim();
            orderNum = Integer.parseInt(orderID.substring(2));
        }
        return orderNum;
    }
    // 将订单信息插入数据库
    public static void insertOrder(String orderID, String userID, String orderTime, String totalMoney){
        String sql="insert into Orders values('" + orderID + "', '" + userID + "', '" + orderTime + "', " + totalMoney + ", 0)";
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
    // 获取订单信息
    public static String[][] getOrders(String userID) throws SQLException {
        String sql="select OrderID,OrderTime,TotalMoney,HasPaid from Orders where UserID = '" + userID + "'";
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        st = con.createStatement();
        rs = st.executeQuery(sql);
        String[][] orders = new String[100][4];
        int i = 0;
        while (rs.next()) {
            orders[i][0] = rs.getString(1);
            orders[i][1] = rs.getString(2);
            orders[i][2] = rs.getString(3);
            orders[i][3] = rs.getString(4);
            i++;
        }
        return orders;
    }
    // 结算订单
    public static void payOrder(String orderID) throws SQLException {
        String sql="update Orders set HasPaid = 1 where OrderID = '" + orderID + "'";
        // TODO：触发器，将订单项中的HasPaid也改为1，自动减少库存
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        st = con.createStatement();
        st.executeUpdate(sql);
        st.close();
        con.close();
    }
}
