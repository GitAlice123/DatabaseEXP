package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import Global.Global;


/**
 * @Description: 用户主界面：两个按钮入口
 * 1. 用户商品界面
 * 2. 用户订单界面
 */
public class UserView {
    JFrame frame;
    JPanel panel;
    public static void main(String[] args) {
        new UserView();
    }
    public UserView(){
        initComponents();
    }
    private void initComponents() {
        frame = new JFrame("用户主界面");
        frame.setSize(400, 300);
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
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JButton productButton = new JButton("商品");
        productButton.setBounds(100, 100, 80, 25);
        panel.add(productButton);
        JButton orderButton = new JButton("订单");
        orderButton.setBounds(200, 100, 80, 25);
        panel.add(orderButton);
        // 返回登录界面按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(150, 150, 80, 25);
        panel.add(backButton);
        // 返回登录界面按钮监听器
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginView();
            }
        });
        // 商品按钮监听器
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new UserProductView();
            }
        });
        // 订单按钮监听器
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new UserOrderView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
