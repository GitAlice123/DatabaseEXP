import java.sql.*;
public class TestConnection {
    public static void main(String args[]){
        Connection con;
        Statement st;
        ResultSet rs;
        String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=online_shopping;trustServerCertificate=true";
        String userName="sa";
        String userPwd="yc030316";
        String sql="select * from Products";
        try {
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String ProductID = rs.getString(1);
                String ProductName = rs.getString(2);
                String Price = rs.getString(3);
                String StockQuantity = rs.getString(4);
                System.out.println("学号：" + ProductID + "\n姓名：" + ProductName + "\n性别：" + Price
                        + "\n出生日期：" + StockQuantity);
            }
            rs.close();
            st.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("数据库连接失败！！！");
        }

    }
}