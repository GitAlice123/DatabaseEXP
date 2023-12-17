package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Global.Global;
import DAO.*;

/**
 * @Description: 卖家销售记录界面
 * 展示商品销售记录：商品ID，商品名称，销售数量
 * 展示不同年度的销售总额
 */
public class SellerRecordView {
    JFrame frame;
    JPanel panel;
    public static void main(String[] args) {
        new SellerRecordView();
    }
    public SellerRecordView(){
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
        String[] columnNames = {"商品编号", "商品名称", "销售数量"};
        //  数据来源：OrderItemDAO
        String[][] products = OrderItemDAO.getPaidItemsInfo();
        JTable table = new JTable(products, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 600, 300);
        panel.add(scrollPane);

        // 展示不同年度的销售总额,用下拉框选择年份，点击“查询”按钮，展示该年度的销售总额
        JLabel yearLabel = new JLabel("年份:");
        yearLabel.setBounds(50, 320, 80, 25);
        panel.add(yearLabel);
        String[] years = {"2020", "2021", "2022"};
        JComboBox yearComboBox = new JComboBox(years);
        yearComboBox.setBounds(100, 320, 80, 25);
        panel.add(yearComboBox);
        JButton queryButton = new JButton("查询年度销售总额");
        queryButton.setBounds(220, 320, 150, 25);
        panel.add(queryButton);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的年份
                String year = (String) yearComboBox.getSelectedItem();
                // 展示该年度的销售总额
                // 数据来源：OrderDAO
                String total = "1000";
                JOptionPane.showMessageDialog(null, "该年度的销售总额为：" + total);
            }
        });

        // 返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(400, 320, 80, 25);
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SellerView();
            }
        });
    }
}
