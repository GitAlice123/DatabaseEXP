package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DAO.ProductDAO;


/**
 * @Description: 卖家界面：展示商品信息并进行修改
 */
public class SellerProductView {
    JFrame frame;
    JPanel panel;
    public static void main(String[] args) {
        new SellerProductView();
    }
    public SellerProductView(){
        initComponents();
    }
    private void initComponents() {
        frame = new JFrame("卖家界面");
        frame.setSize(600, 400);
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
        // 直接展示商品表格，并可以选中某一行，点击“修改”按钮，弹出对话框，修改商品信息
        // 数据来源：ProductDAO
        panel.setLayout(null);
        String[] columnNames = {"商品编号", "商品名称", "商品价格", "商品库存"};
        String[][] products = ProductDAO.getProducts();
        JTable table = new JTable(products, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 600, 300);
        panel.add(scrollPane);
        JButton modifyButton = new JButton("修改");
        modifyButton.setBounds(250, 300, 80, 25);
        panel.add(modifyButton);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的行
                int row = table.getSelectedRow();
                // 获取选中行的商品编号
                String productID = (String) table.getValueAt(row, 0);
                // 获取选中行的商品名称
                String productName = (String) table.getValueAt(row, 1);
                // 获取选中行的商品价格
                String productPrice = (String) table.getValueAt(row, 2);
                // 获取选中行的商品库存
                String productStock = (String) table.getValueAt(row, 3);
                // 弹出对话框，修改商品信息
                JFrame modifyFrame = new JFrame("修改商品信息");
                modifyFrame.setSize(400, 300);
                modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel modifyPanel = new JPanel();
                modifyFrame.add(modifyPanel);
                modifyPanel.setLayout(null);
                JLabel productIDLabel = new JLabel("商品编号:");
                productIDLabel.setBounds(50, 50, 80, 25);
                modifyPanel.add(productIDLabel);
                JTextField productIDText = new JTextField(20);
                productIDText.setBounds(150, 50, 165, 25);
                modifyPanel.add(productIDText);
                productIDText.setText(productID);
                productIDText.setEditable(false);
                JLabel productNameLabel = new JLabel("商品名称:");
                productNameLabel.setBounds(50, 80, 80, 25);
                modifyPanel.add(productNameLabel);
                JTextField productNameText = new JTextField(20);
                productNameText.setBounds(150, 80, 165, 25);
                modifyPanel.add(productNameText);
                productNameText.setText(productName);
                JLabel productPriceLabel = new JLabel("商品价格:");
                productPriceLabel.setBounds(50, 110, 80, 25);
                modifyPanel.add(productPriceLabel);
                JTextField productPriceText = new JTextField(20);
                productPriceText.setBounds(150, 110, 165, 25);
                modifyPanel.add(productPriceText);
                productPriceText.setText(productPrice);
                JLabel productStockLabel = new JLabel("商品库存:");
                productStockLabel.setBounds(50, 140, 80, 25);
                modifyPanel.add(productStockLabel);
                JTextField productStockText = new JTextField(20);
                productStockText.setBounds(150, 140, 165, 25);
                modifyPanel.add(productStockText);
                productStockText.setText(productStock);
                JButton confirmButton = new JButton("确定");
                confirmButton.setBounds(50, 200, 80, 25);
                modifyPanel.add(confirmButton);
                JButton cancelButton = new JButton("取消");
                cancelButton.setBounds(250, 200, 80, 25);
                modifyPanel.add(cancelButton);
                modifyFrame.setVisible(true);
                // 设置一打开就是在屏幕正中间
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension frameSize = modifyFrame.getSize();
                if (frameSize.width > screenSize.width) {
                    frameSize.width = screenSize.width;
                }
                if (frameSize.height > screenSize.height) {
                    frameSize.height = screenSize.height;
                }
                modifyFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
                // 确定按钮监听器
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 获取修改后的商品名称
                        String productName = productNameText.getText();
                        // 获取修改后的商品价格
                        String productPrice = productPriceText.getText();
                        // 获取修改后的商品库存
                        String productStock = productStockText.getText();
                        // 将修改后的商品信息更新到数据库
                        ProductDAO.updateProduct(productID, productName, productPrice, productStock);
                        // 关闭修改商品信息界面
                        modifyFrame.dispose();
                        // 关闭卖家界面
                        frame.dispose();
                        // 重新打开卖家界面
                        new SellerProductView();
                    }
                });
                // 取消按钮监听器
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 关闭修改商品信息界面
                        modifyFrame.dispose();
                    }
                });
            }
        });
        // 加一个返回按钮，返回登录界面
        JButton returnButton = new JButton("返回登录界面");
        returnButton.setBounds(210, 330, 150, 25);
        panel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginView();
            }
        });
    }
}
