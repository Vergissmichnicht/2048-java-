package cqx.priv.serv;

/**
 * @author Qingxin Chen
 * @date 2020/12/26 - 23:28
 **/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Image;

public class Game extends JFrame {
   // private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        Game UI = new Game();
        UI.InitializeUI();
    }

    // 用于存放数据的数组
    private int Numbers[][] = new int[4][4];

    private void InitializeUI() {
        //配置主框体
        this.setTitle("2048");
        this.setLocation(450, 100);
        this.setSize(400, 500);
        this.setLayout(null); //默认流式布局

        // 开始游戏按钮
        ImageIcon imgicon = new ImageIcon("res/start.png");
        imgicon.setImage(imgicon.getImage().getScaledInstance(130, 060,Image.SCALE_DEFAULT));  //压缩图片
        JButton bt = new JButton(imgicon);
        bt.setFocusable(false);
        bt.setBorderPainted(false);
        bt.setFocusPainted(false);
        bt.setContentAreaFilled(false);
        bt.setBounds(5, 10, 120, 30);// 设置按钮的x，y坐标位置和宽度与高度
        this.add(bt);

        //后退一步按钮
        ImageIcon backicon = new ImageIcon("res/back.png");
        imgicon.setImage(imgicon.getImage().getScaledInstance(120, 030,Image.SCALE_DEFAULT));
        JButton back = new JButton(backicon);
        back.setFocusable(false);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.setBounds(270, 10, 120, 30);
        this.add(back);

        // 帮助按钮
        ImageIcon imgicon2 = new ImageIcon("res/help.png");
        imgicon.setImage(imgicon.getImage().getScaledInstance(70, 030,Image.SCALE_DEFAULT));
        JButton about = new JButton(imgicon2);
        about.setFocusable(false);
        about.setBorderPainted(false);
        about.setFocusPainted(false);
        about.setContentAreaFilled(false);
        about.setBounds(160, 10, 70, 30);
        this.add(about);

        // 分数显示
        JLabel lb = new JLabel("分数：0");
        lb.setBounds(40, 45, 120, 30);
        lb.setFont(new Font("正楷", Font.CENTER_BASELINE, 18));
        lb.setForeground(new Color(0x000000));
        this.add(lb);

        //静音按钮
        JCheckBox isSoundBox=new JCheckBox("静音");
        isSoundBox.setBounds(290, 45, 120, 30);
        isSoundBox.setFont(new Font("幼圆", Font.CENTER_BASELINE, 18));
        isSoundBox.setFocusable(false);
        isSoundBox.setBorderPainted(false);
        isSoundBox.setFocusPainted(false);
        isSoundBox.setContentAreaFilled(false);
        this.add(isSoundBox);

        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);// 显示界面

        //
        RunStep cl = new RunStep(this, Numbers, lb, bt, about,back,isSoundBox);
        bt.addActionListener(cl);
        about.addActionListener(cl);
        back.addActionListener(cl);
        isSoundBox.addActionListener(cl);
        this.addKeyListener(cl);

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);  //重写paint（）

        //大矩形框
        g.setColor(new Color(0xBBADB0));
        g.fillRoundRect(15, 110, 370, 370, 15, 15);// 矩形框画圆角

        //小矩形框
        g.setColor(new Color(0xCDC1F4));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);// 小矩形框
            }
        }

        // 调整数字的位置并上色
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Numbers[j][i] != 0) {
                    int FontSize = 30;
                    int MoveX = 0, MoveY = 0;
                    switch (Numbers[j][i]) { //给对应数字上色
                        case 2:
                            g.setColor(new Color(0xeea4ca));
                            FontSize = 30;
                            MoveX = 0;
                            MoveY = 0;
                            break;
                        case 4:
                            g.setColor(new Color(0xede0c8));
                            FontSize = 30;
                            MoveX = 0;
                            MoveY = 0;
                            break;
                        case 8:
                            g.setColor(new Color(0xf3b179));
                            FontSize = 30;
                            MoveX = 0;
                            MoveY = 0;
                            break;
                        case 16:
                            g.setColor(new Color(0xf59563));
                            FontSize = 29;
                            MoveX = -5;
                            MoveY = 0;
                            break;
                        case 32:
                            g.setColor(new Color(0xf67c5f));
                            FontSize = 29;
                            MoveX = -5;
                            MoveY = 0;
                            break;
                        case 64:
                            g.setColor(new Color(0xf65e3b));
                            FontSize = 29;
                            MoveX = -5;
                            MoveY = 0;
                            break;
                        case 128:
                            g.setColor(new Color(0xedcf72));
                            FontSize = 28;
                            MoveX = -10;
                            MoveY = 0;
                            break;
                        case 256:
                            g.setColor(new Color(0xedcc61));
                            FontSize = 28;
                            MoveX = -10;
                            MoveY = 0;
                            break;
                        case 512:
                            g.setColor(new Color(0xedc850));
                            FontSize = 28;
                            MoveX = -10;
                            MoveY = 0;
                            break;
                        case 1024:
                            g.setColor(new Color(0xedc53f));
                            FontSize = 27;
                            MoveX = -15;
                            MoveY = 0;
                            break;
                        case 2048:
                            g.setColor(new Color(0xedc22e));
                            FontSize = 27;
                            MoveX = -15;
                            MoveY = 0;
                            break;
                        default:
                            g.setColor(new Color(0x000000));
                            break;
                    }
                    //矩形框上色
                    g.fillRoundRect(25 + i * 90, 120 + j * 90, 80, 80, 15, 15);
                    g.setColor(new Color(0x000A00));
                    g.setFont(new Font("Kristen ITC", Font.PLAIN, FontSize));
                    //重新绘图
                    g.drawString(Numbers[j][i] + "", 25 + i * 90 + 30 + MoveX,
                            120 + j * 90 + 50 + MoveY);
                }
            }
        }
    }

}
