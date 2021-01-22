package com.sunwise.convert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Objects;


public class SeleteDir implements ActionListener {
    JFrame frame = new JFrame();// 框架布局
    JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
    Container con = new Container();//
    JLabel label1 = new JLabel("请选择转换源路径:");
    JLabel label2 = new JLabel("请选择转换目标路径:");
    JTextField text1 = new JTextField();// TextField 目录的路径
    JTextField text2 = new JTextField();// 文件的路径
    JButton button1 = new JButton("选择");// 选择
    JButton button2 = new JButton("选择");// 选择
    JFileChooser jfc = new JFileChooser();// 文件选择器
    JButton button3 = new JButton("下一步");//

    SeleteDir() {
        jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘

        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        frame.setSize(600, 400);// 设定窗口大小
        frame.setContentPane(tabPane);// 设置布局
        label1.setBounds(30, 30, 150, 40);
        text1.setBounds(185, 30, 220, 30);
        button1.setBounds(410, 30, 150, 35);
        label2.setBounds(30, 165, 150, 40);
        text2.setBounds(185, 165, 220, 30);
        button2.setBounds(410, 165, 150, 35);
        button3.setBounds(300, 250, 100, 35);
        button1.addActionListener(this); // 添加事件处理
        button2.addActionListener(this); // 添加事件处理
        button3.addActionListener(this); // 添加事件处理
        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(label2);
        con.add(text2);
        con.add(button2);
        con.add(button3);
        frame.setVisible(true);// 窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
        tabPane.add("c++转c程序", con);// 添加布局1
    }

    /**
     * 事件监听的方法
     */
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;
            } else {
                File f = jfc.getSelectedFile();// f为选择到的目录
                text1.setText(f.getAbsolutePath());
            }
        }
        // 绑定到选择文件，先择文件事件
        if (e.getSource().equals(button2)) {
            jfc.setFileSelectionMode(1);// 设定只能选择到文件
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;// 撤销则返回
            } else {
                File f = jfc.getSelectedFile();// f为选择到的文件
                text2.setText(f.getAbsolutePath());
            }
        }
        if (e.getSource().equals(button3)) {
            // 弹出对话框可以改变里面的参数具体得靠大家自己去看，时间很短
            //JOptionPane.showMessageDialog(null, "弹出对话框的实例！", "提示", 2);
            if (Objects.equals(text1.getText(), "") || Objects.equals(text2.getText(), "")) {
                JOptionPane.showMessageDialog(null, "源路径或者目标路径未选择!", "错误", 0);
            } else {
                CopyDirectory copyDirectory=new CopyDirectory();
                copyDirectory.copy(text1.getText(),text2.getText());
                /* 获取target文件夹中所有的.cpp,.h,.hpp文件 */
                GetPaths allCppAHppFiles=new GetPaths(text2.getText());
                List<String> allCppList = allCppAHppFiles.getcPaths();
                List<String> allHppList = allCppAHppFiles.gethPathFiles();
                if(allCppList.size()+allHppList.size()>0){
                    this.frame.dispose();//点击按钮时frame1销毁,new一个frame
                    new SeleteFile().initialize(allCppList,allHppList);
                }else{
                    JOptionPane.showMessageDialog(null, "目标路径中不含需要转换的文件格式!", "错误", 0);
                }

            }

        }
    }

}