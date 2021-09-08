package cqx.priv.serv;

/**
 * @author Qingxin Chen
 * @date 2020/12/26 - 23:29
 **/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class RunStep extends KeyAdapter implements ActionListener {

    private Game UI;// 界面对象
    private int Numbers[][];// 存放数据的数组
    private Random rand = new Random();
    private int BackUp[][]= new int[4][4];//备份数组，供回退时使用
    private int BackUp2[][]= new int[4][4];//备份数组，供起失败时退回使用
    public JLabel lb; //标签
    int score = 0; //分数
    int tempscore,tempscore2;//记录回退的分数值
    public JButton bt,help,back; //开始、帮助、回退按钮
    public JCheckBox isSoundBox; //静音按钮
    private boolean isWin=false,relive=false,hasBack=false,isSound=true;//状态控制



    public RunStep(Game UI, int Numbers[][], JLabel lb, JButton bt, JButton help, JButton back, JCheckBox isSoundBox) {
        this.UI = UI;
        this.Numbers = Numbers;
        this.lb = lb;
        this.bt=bt;
        this.help=help;
        this.back=back;
        this.isSoundBox=isSoundBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //判断点击的按钮c

        //开始游戏
        if(e.getSource() ==bt ){
            isWin=false;
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                    Numbers[i][j] = 0;
            //显示分数
            score = 0;
            lb.setText("分数：" + score);
            int r1 = rand.nextInt(4);
            int r2 = rand.nextInt(4);
            int c1 = rand.nextInt(4);
            int c2 = rand.nextInt(4);

            while (r1 == r2 && c1 == c2) {
                r2 = rand.nextInt(4);
                c2 = rand.nextInt(4);
            }

            // 随机生成数字（2或者4），并填入相应位置
            int value1 = rand.nextInt(2) * 2 + 2;
            int value2 = rand.nextInt(2) * 2 + 2;
            Numbers[r1][c1] = value1;
            Numbers[r2][c2] = value2;
            UI.paint(UI.getGraphics());
        }
        //帮助
        else if(e.getSource()==help){
            JOptionPane.showMessageDialog(UI, "游戏规则：\n"
                    + "普通的2048小游戏，点击“开始游戏”开始玩耍吧！\n");
        }
        //后退一步
        else if(e.getSource()==back&&hasBack==false){
            hasBack=true;
            if(relive==false){
                score=tempscore;
                lb.setText("分数：" + score);
                for(int i=0;i<BackUp.length;i++){
                    Numbers[i]=Arrays.copyOf(BackUp[i], BackUp[i].length);
                }
            }
            else{
                score=tempscore2;
                lb.setText("分数：" + score);
                for(int i=0;i<BackUp2.length;i++){
                    Numbers[i]=Arrays.copyOf(BackUp2[i], BackUp2[i].length);
                }
                relive=false;
            }
            UI.paint(UI.getGraphics());
        }
        //开关声音
        else if(e.getSource().equals(isSoundBox)){
            if (isSoundBox.isSelected())
                isSound=false;
            else
                isSound=true;
        }
    }





    // 键盘监听
    public void keyPressed(KeyEvent event) {

        int Counter = 0;// 判断移动
        int NumCounter = 0;// 判断数字框是否已满
        int NumNearCounter = 0;// 统计相邻格子数字相同的个数
        // 注：方向键键值：左：37上：38右：39下：40
        hasBack = false;

        //上一步不为空时
        if (BackUp != null || BackUp.length != 0) {
            tempscore2 = tempscore;// 先把分数备份好
            // 调用java.util.Arrays.copyOf()方法复制数组，实现备份
            for (int i = 0; i < BackUp.length; i++) {
                BackUp2[i] = Arrays.copyOf(BackUp[i], BackUp[i].length);
            }
        }

        tempscore = score;// 备份分数
        // 调用java.util.Arrays.copyOf()方法复制数组，实现备份
        for (int i = 0; i < Numbers.length; i++) {
            BackUp[i] = Arrays.copyOf(Numbers[i], Numbers[i].length);
        }

        if (isWin == false) {
            switch (event.getKeyCode()) {

                case 37:
                    // 向左移动
                    if (isSound == true)
                        new PlaySound("move.wav").start();
                    for (int h = 0; h < 4; h++)
                        for (int l = 0; l < 4; l++)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = l - 1;
                                while (pre >= 0 && Numbers[h][pre] == 0) {
                                    Numbers[h][pre] = temp;
                                    Numbers[h][pre + 1] = 0;
                                    pre--;
                                    Counter++;
                                }
                            }
                    for (int h = 0; h < 4; h++)
                        for (int l = 0; l < 4; l++)
                            if (l + 1 < 4
                                    && (Numbers[h][l] == Numbers[h][l + 1])
                                    && (Numbers[h][l] != 0 || Numbers[h][l + 1] != 0)) {
                                if (isSound == true)
                                    new PlaySound("merge.wav").start();
                                Numbers[h][l] = Numbers[h][l] + Numbers[h][l + 1];
                                Numbers[h][l + 1] = 0;
                                Counter++;
                                score += Numbers[h][l];
                                if (Numbers[h][l] == 2048) {
                                    isWin = true;
                                }
                            }

                    for (int h = 0; h < 4; h++)
                        for (int l = 0; l < 4; l++)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = l - 1;
                                while (pre >= 0 && Numbers[h][pre] == 0) {
                                    Numbers[h][pre] = temp;
                                    Numbers[h][pre + 1] = 0;
                                    pre--;
                                    Counter++;
                                }
                            }
                    break;

                case 39:// 向右移动
                    if (isSound == true)
                        new PlaySound("move.wav").start();
                    for (int h = 3; h >= 0; h--)
                        for (int l = 3; l >= 0; l--)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = l + 1;
                                while (pre <= 3 && Numbers[h][pre] == 0) {
                                    Numbers[h][pre] = temp;
                                    Numbers[h][pre - 1] = 0;
                                    pre++;
                                    Counter++;
                                }
                            }

                    for (int h = 3; h >= 0; h--)
                        for (int l = 3; l >= 0; l--)
                            if (l + 1 < 4
                                    && (Numbers[h][l] == Numbers[h][l + 1])
                                    && (Numbers[h][l] != 0 || Numbers[h][l + 1] != 0)) {
                                if (isSound == true)
                                    new PlaySound("merge.wav").start();
                                Numbers[h][l + 1] = Numbers[h][l]
                                        + Numbers[h][l + 1];
                                Numbers[h][l] = 0;
                                Counter++;
                                score += Numbers[h][l + 1];
                                if (Numbers[h][l + 1] == 2048) {
                                    isWin = true;
                                }
                            }
                    for (int h = 3; h >= 0; h--)
                        for (int l = 3; l >= 0; l--)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = l + 1;
                                while (pre <= 3 && Numbers[h][pre] == 0) {
                                    Numbers[h][pre] = temp;
                                    Numbers[h][pre - 1] = 0;
                                    pre++;
                                    Counter++;
                                }
                            }
                    break;

                case 38:
                    // 向上移动
                    if (isSound == true)
                        new PlaySound("move.wav").start();
                    for (int l = 0; l < 4; l++)
                        for (int h = 0; h < 4; h++)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = h - 1;
                                while (pre >= 0 && Numbers[pre][l] == 0) {
                                    Numbers[pre][l] = temp;
                                    Numbers[pre + 1][l] = 0;
                                    pre--;
                                    Counter++;
                                }
                            }
                    for (int l = 0; l < 4; l++)
                        for (int h = 0; h < 4; h++)
                            if (h + 1 < 4
                                    && (Numbers[h][l] == Numbers[h + 1][l])
                                    && (Numbers[h][l] != 0 || Numbers[h + 1][l] != 0)) {
                                if (isSound == true)
                                    new PlaySound("merge.wav").start();
                                Numbers[h][l] = Numbers[h][l] + Numbers[h + 1][l];
                                Numbers[h + 1][l] = 0;
                                Counter++;
                                score += Numbers[h][l];
                                if (Numbers[h][l] == 2048) {
                                    isWin = true;
                                }
                            }

                    for (int l = 0; l < 4; l++)
                        for (int h = 0; h < 4; h++)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = h - 1;
                                while (pre >= 0 && Numbers[pre][l] == 0) {
                                    Numbers[pre][l] = temp;
                                    Numbers[pre + 1][l] = 0;
                                    pre--;
                                    Counter++;
                                }
                            }
                    break;

                case 40:
                    // 向下移动
                    if (isSound == true)
                        new PlaySound("move.wav").start();
                    for (int l = 3; l >= 0; l--)
                        for (int h = 3; h >= 0; h--)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = h + 1;
                                while (pre <= 3 && Numbers[pre][l] == 0) {
                                    Numbers[pre][l] = temp;
                                    Numbers[pre - 1][l] = 0;
                                    pre++;
                                    Counter++;
                                }
                            }
                    for (int l = 3; l >= 0; l--)
                        for (int h = 3; h >= 0; h--)
                            if (h + 1 < 4
                                    && (Numbers[h][l] == Numbers[h + 1][l])
                                    && (Numbers[h][l] != 0 || Numbers[h + 1][l] != 0)) {
                                if (isSound == true)
                                    new PlaySound("merge.wav").start();
                                Numbers[h + 1][l] = Numbers[h][l]
                                        + Numbers[h + 1][l];
                                Numbers[h][l] = 0;
                                Counter++;
                                score += Numbers[h + 1][l];
                                if (Numbers[h + 1][l] == 2048) {
                                    isWin = true;
                                }
                            }

                    for (int l = 3; l >= 0; l--)
                        for (int h = 3; h >= 0; h--)
                            if (Numbers[h][l] != 0) {
                                int temp = Numbers[h][l];
                                int pre = h + 1;
                                while (pre <= 3 && Numbers[pre][l] == 0) {
                                    Numbers[pre][l] = temp;
                                    Numbers[pre - 1][l] = 0;
                                    pre++;
                                    Counter++;
                                }
                            }
                    break;

            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (Numbers[i][j] == Numbers[i][j + 1]
                            && Numbers[i][j] != 0) {
                        NumNearCounter++;
                    }
                    if (Numbers[i][j] == Numbers[i + 1][j]
                            && Numbers[i][j] != 0) {
                        NumNearCounter++;
                    }
                    if (Numbers[3][j] == Numbers[3][j + 1]
                            && Numbers[3][j] != 0) {
                        NumNearCounter++;
                    }
                    if (Numbers[i][3] == Numbers[i + 1][3]
                            && Numbers[i][3] != 0) {
                        NumNearCounter++;
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (Numbers[i][j] != 0) {
                        NumCounter++;
                    }
                }
            }
            if (Counter > 0) {

                lb.setText("分数：" + score);
                int r1 = rand.nextInt(4);
                int c1 = rand.nextInt(4);
                while (Numbers[r1][c1] != 0) {
                    r1 = rand.nextInt(4);
                    c1 = rand.nextInt(4);
                }
                int value1 = rand.nextInt(2) * 2 + 2;
                Numbers[r1][c1] = value1;
            }

            if (isWin == true){
                UI.paint(UI.getGraphics());
                JOptionPane.showMessageDialog(UI, "恭喜你赢了!\n您的最终得分为：" + score);
            }

            if (NumCounter == 16 && NumNearCounter == 0) {
                relive = true;
                JOptionPane.showMessageDialog(UI, "没地方可以合并咯!!"
                        + "\n恭喜您，您输了orz" + "\n点击“后退一步”，即可退回上一步继续游戏哟\n");
            }
            UI.paint(UI.getGraphics());
        }

    }

}
