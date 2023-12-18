package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DAO.*;
import Global.Global;
import static DAO.OrderItemDAO.specialQuery;

/*
    * @Description: 管理员界面
    * 正中间是一个JTable，显示所有OrderItem信息
    * 下面有三个按钮，分别是“返回登录界面“、”查询1“、”查询2“
    * 查询1前面有一个JLabel，写着“查询购买了不少于5个库存小于50个的商品的用户的支付时间在2023年6月20日之前的所有订单内容”
    * 查询2前面有一个JLabel，写着“查询购买了只有一个订单有购买过的商品的客户的名字”
    * 点击“查询1”后，JTable显示查询结果
    * 点击“查询2”后，弹出一个JOptionPane，显示查询结果
 */
public class AdminView {
    static JFrame frame;
    public static void main(String[] args) {
        frame = new JFrame("管理员界面");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
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

    public AdminView(){
        frame = new JFrame("管理员界面");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
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
    private static void placeComponents(JPanel panel) {
        // 表格放在正中央，下面有三个按钮，分别是“返回登录界面“、”查询1“、”查询2“
        // 下面三个按钮的布局：
        // 返回登录界面在右下角
        // 查询1在正下方，查询2在查询1的正下方
        // 每个查询的label在对应按钮的上面，过长的label分两行
        panel.setLayout(null);
        JLabel query1Label = new JLabel("查询购买了不少于5个库存小于50个的商品的用户的支付时间在2023年6月20日之前的所有订单内容");
        query1Label.setBounds(50, 460, 700, 25);
        panel.add(query1Label);
        JButton query1Button = new JButton("查询1");
        query1Button.setBounds(50, 480, 80, 25);
        panel.add(query1Button);
        JLabel query2Label = new JLabel("查询购买了只有一个订单有购买过的商品的客户的名字");
        query2Label.setBounds(50, 500, 700, 25);
        panel.add(query2Label);
        JButton query2Button = new JButton("查询2");
        query2Button.setBounds(50, 520, 80, 25);
        panel.add(query2Button);
        JButton returnButton = new JButton("返回登录界面");
        returnButton.setBounds(520, 490, 120, 25);
        panel.add(returnButton);
        JButton backButton = new JButton("从查询返回");
        backButton.setBounds(520, 520, 120, 25);
        panel.add(backButton);
        String[] columnNames = {"订单项编号", "订单编号", "商品编号", "购买量", "是否已付款"};
        String[][] tableValues = OrderItemDAO.getAllItemsInfo();
        JTable table = new JTable(tableValues, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 50, 600, 400);
        panel.add(scrollPane);
        query1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = {"订单项编号", "订单编号", "商品编号", "购买量", "是否已付款"};
                String[][] tableValues = OrderItemDAO.specialQuery();

                JTable table = new JTable(tableValues, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(100, 50, 600, 400);
                panel.add(scrollPane);
            }
        });
        query2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[]userNames = OrderItemDAO.getSpecialUser();
                // 弹出一个JOptionPane，显示查询结果
                JOptionPane.showMessageDialog(null, userNames, "查询结果", JOptionPane.PLAIN_MESSAGE);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginView();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = {"订单项编号", "订单编号", "商品编号", "购买量", "是否已付款"};
                String[][] tableValues = OrderItemDAO.getAllItemsInfo();
                JTable table = new JTable(tableValues, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(100, 50, 600, 400);
                panel.add(scrollPane);
            }
        });
    }

}
