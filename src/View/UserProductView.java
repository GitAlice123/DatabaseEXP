package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import Global.Global;
import DAO.*;


/*
 * @Description: 用户商品，显示所有商品信息
 * 使用卡片布局，卡片1是所有商品信息，卡片2是购物车信息
 * 卡片1正中央是商品信息表格，数据从ProductDAO中获取
 * 卡片1右上角是购物车按钮，点击后切换到卡片2
 * 选中卡片1表格中的某一行，点击“加入购物车”按钮，弹出对话框，输入购买数量，点击“确定”按钮，将商品加入购物车
 * 卡片2正中央是购物车信息表格，数据从ProductDAO中获取
 * 卡片2右上角是返回按钮，点击后切换到卡片1
 * 选中卡片2表格中的某一行，点击“删除”按钮，弹出对话框，确认删除，点击“确定”按钮，将商品从购物车中删除
 * 卡片2右下角是"生成订单"按钮，点击后弹出对话框，确认生成订单，点击“确定”按钮，将购物车中的商品生成订单
 */

public class UserProductView {
    // 使用一个pair类来存储商品ID和购买数量
    class Pair {
        String buyID;
        String buyQuantity;
        public Pair(String buyID, String buyQuantity) {
            this.buyID = buyID;
            this.buyQuantity = buyQuantity;
        }
    }
    // 设置一个vector来存储pair
    Vector<Pair> buyIDlist = new Vector<>();
    CardLayout cardLayout;
    JPanel card1;
    JPanel panel;
    JPanel card2;
    String[][] products2;
    JFrame frame;
    public static void main(String[] args) {
        new UserProductView();
    }
    public UserProductView(){
        initComponents();
    }
    public void showCartTable(){
        // 卡片2
        card2 = new JPanel();
        card2.setLayout(null);
        panel.add(card2, "card2");
        // 卡片2正中央是购物车信息表格，数据从ProductDAO中获取
        String[] columnNames2 = {"商品编号", "商品名称", "商品价格", "商品库存", "购买数量"};
        // 从buyIDlist中获取商品ID，然后从数据库中获取商品信息
        Vector<String> buyID = new Vector<>();
        System.out.println(buyIDlist.size());
        for (int i = 0; i < buyIDlist.size(); i++) {
            buyID.add(buyIDlist.get(i).buyID);
        }
        products2 = ProductDAO.getProducts(buyID);
        String[][] products_temp;
        if(products2 == null){
            products_temp = new String[0][0];
        }
        else{
            // 添加最后一列购买数量
            // 新建一个string数组，长度是products2的长度，最后一列是购买数量
            products_temp = new String[this.products2.length][this.products2[0].length + 1];
            for (int i = 0; i < this.products2.length; i++) {
                for (int j = 0; j < this.products2[0].length; j++) {
                    products_temp[i][j] = this.products2[i][j];
                }
            }
            // 将购买数量添加到最后一列
            for (int i = 0; i < buyIDlist.size(); i++) {
                products_temp[i][4] = buyIDlist.get(i).buyQuantity;
            }
        }


        JTable table2 = new JTable(products_temp, columnNames2);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setBounds(50, 50, 700, 400);
        card2.add(scrollPane2);
        // 卡片2右上角是返回按钮，点击后切换到卡片1
        JButton backButton = new JButton("返回");
        backButton.setBounds(600, 10, 100, 30);
        card2.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "card1");
            }
        });
        // 选中卡片2表格中的某一行，点击“删除”按钮，弹出对话框，确认删除，点击“确定”按钮，将商品从购物车中删除
        JButton deleteButton = new JButton("删除");
        deleteButton.setBounds(50, 500, 100, 30);
        card2.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table2.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请选中一行！！！");
                    return;
                }
                String buyID = products_temp[row][0];
                int result = JOptionPane.showConfirmDialog(null, "确认删除？", "确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // 将商品从购物车中删除
                    for (int i = 0; i < buyIDlist.size(); i++) {
                        if (buyIDlist.get(i).buyID.equals(buyID)) {
                            buyIDlist.remove(i);
                            break;
                        }
                    }
                    // 重新展示购物车信息
                    // 让card2立刻刷新
                    showCartTable();

                    JOptionPane.showMessageDialog(null, "删除成功！！！");
                }
            }
        });
        // 卡片2右下角是"生成订单"按钮，点击后弹出对话框，确认生成订单，点击“确定”按钮，将购物车中的商品生成订单
        JButton orderButton = new JButton("生成订单");
        orderButton.setBounds(650, 500, 100, 30);
        card2.add(orderButton);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "确认生成订单？", "确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    String orderID = null;
                    // 将购物车中的商品生成订单
                    for (int i = 0; i < buyIDlist.size(); i++) {
                        String buyQuantity = buyIDlist.get(i).buyQuantity;
                        String buyPrice = products2[i][2];
                        // 计算总价格，数据类型是float
                        float totalMoney = Float.parseFloat(buyQuantity) * Float.parseFloat(buyPrice);
                        // 转换成String
                        String totalMoneyString = String.valueOf(totalMoney);
                        try {
                            orderID = OrderDAO.generateOrder(Global.getUserID(), totalMoneyString);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "生成订单成功！！！");


                    // 将购物车中的商品生成订单项
                    for (int i = 0; i < buyIDlist.size(); i++) {
                        String buyID = buyIDlist.get(i).buyID;
                        String buyQuantity = buyIDlist.get(i).buyQuantity;
                        try {
                            OrderItemDAO.generateOrderItem(orderID, buyID, buyQuantity);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    // 生成订单后，购物车清空
                    buyIDlist.clear();
                    // 重新展示购物车信息
                    // 让card2立刻刷新
                    showCartTable();
                }
            }
        });
        // 卡片2左上角是“返回主界面”按钮，点击后返回用户主界面
        JButton returnButton = new JButton("返回主界面");
        returnButton.setBounds(50, 10, 100, 30);
        card2.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new UserView();
            }
        });
    }
    public void showProductTable(){
        // 卡片1
        card1 = new JPanel();
        card1.setLayout(null);
        panel.add(card1, "card1");
        // 卡片1正中央是商品信息表格，数据从ProductDAO中获取
        String[] columnNames = {"商品编号", "商品名称", "商品价格", "商品库存"};
        String[][] products = ProductDAO.getProducts();
        JTable table = new JTable(products, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 700, 400);
        card1.add(scrollPane);
        // 卡片1右上角是购物车按钮，点击后切换到卡片2
        JButton cartButton = new JButton("购物车");
        cartButton.setBounds(600, 10, 100, 30);
        card1.add(cartButton);
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCartTable();
                cardLayout.show(panel, "card2");
            }
        });
        // 选中卡片1表格中的某一行，点击“加入购物车”按钮，弹出对话框，输入购买数量，点击“确定”按钮，将商品加入购物车
        JButton buyButton = new JButton("加入购物车");
        buyButton.setBounds(50, 500, 100, 30);
        card1.add(buyButton);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请选中一行！！！");
                    return;
                }
                String buyID = products[row][0];
                String buyName = products[row][1];
                String buyPrice = products[row][2];
                String buyStockQuantity = products[row][3];
                String buyQuantity = JOptionPane.showInputDialog("请输入购买数量：");
                if (buyQuantity == null) {
                    return;
                }
                if (buyQuantity.equals("")) {
                    JOptionPane.showMessageDialog(null, "请输入购买数量！！！");
                    return;
                }
                if (!buyQuantity.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "请输入数字！！！");
                    return;
                }
                if (Integer.parseInt(buyQuantity) > Integer.parseInt(buyStockQuantity)) {
                    JOptionPane.showMessageDialog(null, "库存不足！！！");
                    return;
                }
                // 将商品加入购物车
                buyIDlist.add(new Pair(buyID, buyQuantity));
                JOptionPane.showMessageDialog(null, "加入购物车成功！！！");
                System.out.println(buyIDlist.size());
            }
        });
    }
    public void setMid(){
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
    private void initComponents(){
        // 使用卡片布局
        frame = new JFrame("商品信息");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.add(panel);
        cardLayout = new CardLayout();
        panel.setLayout(cardLayout);


        showProductTable();
        showCartTable();


        frame.setVisible(true);
        setMid();
    }

}
