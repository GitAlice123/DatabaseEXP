package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Global.Global;

/**
 * @Description: 卖家界面：两个按钮
 * 1. 商品界面
 * 2. 销售记录界面
 */
public class SellerView {
    JFrame frame;
    JPanel panel;
    public static void main(String[] args) {
        new SellerView();
    }
    public SellerView(){
        initComponents();
    }
    private void initComponents() {
        frame = new JFrame("卖家界面");
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
        productButton.setBounds(80, 100, 80, 25);
        panel.add(productButton);
        JButton orderButton = new JButton("销售记录");
        orderButton.setBounds(180, 100, 140, 25);
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
                new SellerProductView();
            }
        });
        // 销售记录按钮监听器
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SellerRecordView();
            }
        });
    }
}
