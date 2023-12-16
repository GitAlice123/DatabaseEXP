package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    * @Description: 用户主界面，展示所有商品信息
    * 使用卡片布局
    * 正中间是一个表格，展示所有商品信息，包括商品ID，商品名称，商品价格，商品库存
    * 数据来源使用DAO层的ProductDAO
    * 可以选中某一行，点击购买按钮，购买该商品
    * 右边是一个搜索框，可以根据商品ID搜索商品
    * 下方是一个购物车按钮，点击可以进入购物车界面，不要离开UserView
    * 购物车界面可以展示所有已经购买的商品，包括商品ID，商品名称，商品价格，商品数量，商品总价
    * 可以选中某一行，点击删除按钮，删除该商品
    * 可以选中某一行，点击修改数量按钮，修改该商品数量
    * 可以点击结算按钮，结算所有商品，生成订单
 */
public class UserView extends JFrame{
    // 使用卡片布局，包含两个卡片，一个是商品信息卡片，一个是购物车卡片
    // 不要写main函数，这是一个需要被其他界面调用的类
    public UserView(){
        JFrame frame = new JFrame("用户界面");
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

    }

}