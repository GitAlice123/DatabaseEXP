package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import Global.Global;
import DAO.OrderDAO;
import DAO.PaymentDAO;

/**
 * @Description: 用户订单界面
 * 1. 显示所有订单信息
 * 2. 选中某一行，点击“结算”按钮，弹出对话框，确认结算，点击“确定”按钮，将订单状态改为“已完成”
 * 数据来源：OrderDAO
 */
public class UserOrderView {
    JFrame frame;
    JPanel panel;
    public static void main(String[] args) throws SQLException {
        new UserOrderView();
    }
    public UserOrderView() throws SQLException {
        initComponents();
    }
    private void initComponents() throws SQLException {
        frame = new JFrame("用户订单界面");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
        // 设置一打开就是在屏幕正中间
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }
    private void placeComponents(JPanel panel) throws SQLException {
        panel.setLayout(null);
        // 显示所有订单信息
        String[] columnNames = {"订单编号", "订购时间", "总金额", "是否已支付"};
        String[][] orders = OrderDAO.getOrders(Global.getUserID());
        JTable table = new JTable(orders, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 800, 400);
        panel.add(scrollPane);
        // 选中某一行，点击“结算”按钮，弹出对话框，确认结算，点击“确定”按钮，将订单状态改为“已完成”
        JButton payButton = new JButton("结算");
        payButton.setBounds(30, 420, 80, 25);
        panel.add(payButton);
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请选中一行！", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String orderID = orders[row][0];
                int result = JOptionPane.showConfirmDialog(null, "确认结算？", "提示", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        OrderDAO.payOrder(orderID);
                        PaymentDAO.generatePayment(orderID);
                        JOptionPane.showMessageDialog(null, "结算成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        new UserOrderView();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        // 加一个返回按钮，返回用户主界面
        JButton returnButton = new JButton("返回主界面");
        returnButton.setBounds(600, 420, 150, 25);
        panel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new UserView();
            }
        });

    }
}
