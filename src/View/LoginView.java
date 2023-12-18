package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Global.Global;

/*
    * @Description: 登录界面
 */
public class LoginView extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("登录界面");
        frame.setSize(400, 300);
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

    public LoginView(){
        JFrame frame = new JFrame("登录界面");
        frame.setSize(400, 300);
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
        panel.setLayout(null);
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(50, 50, 80, 25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(150, 50, 165, 25);
        panel.add(userText);
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 100, 80, 25);
        panel.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 100, 165, 25);
        panel.add(passwordText);
        JLabel identityLabel = new JLabel("身份:");
        identityLabel.setBounds(50, 150, 80, 25);
        panel.add(identityLabel);
        JTextField identityText = new JTextField(20);
        identityText.setBounds(150, 150, 165, 25);
        panel.add(identityText);
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(50, 200, 80, 25);
        panel.add(loginButton);
        JButton exitButton = new JButton("退出");
        exitButton.setBounds(250, 200, 80, 25);
        panel.add(exitButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = String.valueOf(passwordText.getPassword());
                String identity = identityText.getText();
                String userID;
                if ((userID=DAO.UserDAO.login(username, password, identity))!=null) {
                    Global.setUserID(userID);
                    JOptionPane.showMessageDialog(null, "登录成功！");
                    if (identity.equals("ADMIN")) {
                        new AdminView();
                        // 退出登录界面
                        ((JFrame)panel.getRootPane().getParent()).dispose();
                    } else if (identity.equals("CUS")) {
                        new UserView();
                        // 退出登录界面
                        ((JFrame)panel.getRootPane().getParent()).dispose();
                    } else if(identity.equals("SHOP")) {
                        new SellerView();
                        ((JFrame)panel.getRootPane().getParent()).dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "登录失败！");
                }
            }
        });;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}

