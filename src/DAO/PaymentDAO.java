package DAO;
import Global.Global;

import java.sql.*;

/*
    * @Description: 支付DAO，支付信息
    * 包括以下字段：
    * PaymentID 支付编号,格式为Pay+三位数字，如Pay001
    * OrderID 订单编号
    * PaymentTime 支付时间
    * 只需要实现在用户点击“结算”按钮后，将支付信息插入数据库即可
 */
public class PaymentDAO {
    static String dbURL= Global.getDbURL();
    static String userName=Global.getUserName();
    static String userPwd=Global.getUserPwd();
    static Connection con;
    static Statement st;
    static ResultSet rs;

    // 生成支付信息
    public static void generatePayment(String orderID) throws SQLException {
        // 生成支付编号
        String paymentID = generatePaymentID();
        // 获取当前时间
        String paymentTime = getCurrentTime();
        // 将支付信息插入数据库
        insertPayment(paymentID, orderID, paymentTime);
    }

    // 生成支付编号
    public static String generatePaymentID() throws SQLException {
        // 从数据库中获取当前支付数
        int paymentNum = getPaymentNum();
        // 生成支付编号
        return "Pay" + String.format("%03d", paymentNum + 1);
    }

    // 获取当前时间
    public static String getCurrentTime(){
        // 获取当前时间
        java.util.Date date = new java.util.Date();
        // 转换为数据库中的datetime类型
        java.sql.Timestamp currentTime = new java.sql.Timestamp(date.getTime());
        return currentTime.toString();
    }

    // 从数据库中获取当前支付数
    public static int getPaymentNum() throws SQLException {
        // 从数据库中获取当前支付编号的最后一个数字
        String sql="select PaymentID from Payment";
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        st = con.createStatement();
        rs = st.executeQuery(sql);
        int paymentNum = 0;
        while (rs.next()) {
            String paymentID = rs.getString(1);
            // 去除这个String中的末尾空格
            paymentID = paymentID.trim();
            paymentNum = Integer.parseInt(paymentID.substring(3));
        }
        return paymentNum;
    }

    // 将支付信息插入数据库
    public static void insertPayment(String paymentID, String orderID, String paymentTime) throws SQLException {
        String sql = "insert into Payment values(?, ?, ?)";
        con = DriverManager.getConnection(dbURL, userName, userPwd);
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, paymentID);
        ps.setString(2, orderID);
        ps.setString(3, paymentTime);
        ps.executeUpdate();
        ps.close();
        con.close();
    }
}
