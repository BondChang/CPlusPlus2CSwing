package com.sunwise.convert;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Dialog class
 **/
public class SeleteFile extends JDialog {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new SeleteFile().initialize();
//            }
//        });
//    }

    public void initialize(List<String> allCppList, List<String> allHppList) {
        SourceTableModel stm = new SourceTableModel(allCppList, allHppList);
        JTable sourceTable = new JTable(stm);
        sourceTable.getColumnModel().getColumn(0).setCellRenderer(new RadioButtonRenderer());
        sourceTable.getColumnModel().getColumn(0).setCellEditor(new RadioButtonEditor(new JCheckBox()));
        sourceTable.getColumnModel().getColumn(0).setMaxWidth(50);
        sourceTable.setRowHeight(40);// 设置表格行宽
        JPanel panel = new JPanel();
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        panel.add(new JScrollPane(sourceTable));
        add(panel, BorderLayout.CENTER);
        final MyCheckBoxRenderer check = new MyCheckBoxRenderer();
        sourceTable.getColumnModel().getColumn(0).setHeaderRenderer(check);
        sourceTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(sourceTable.getColumnModel().getColumnIndexAtX(e.getX())==0){//如果点击的是第0列，即checkbox这一列
                    JCheckBox Checkbox = (JCheckBox)check;
                    boolean b = !check.isSelected();
                    check.setSelected(b);
                    sourceTable.getTableHeader().repaint();
                    for(int i=0;i<sourceTable.getRowCount();i++){
                        sourceTable.getModel().setValueAt(b, i, 0);//把这一列都设成和表头一样
                    }
                }
            }
        });
        JPanel btnPanel = new JPanel();
//        JButton btnSel = new JButton("全选");
//        btnPanel.add(btnSel);
//        btnSel.addActionListener(new ActionListener() {
//            @Override public void actionPerformed(ActionEvent e) {
//                for(int i=0;i<sourceTable.getRowCount();i++){
//                    sourceTable.getModel().setValueAt(false, i, 0);//把这一列都设成和表头一样
//                }
//
//            }
//        });
        /* 创建一个进度条 */
   //    final JProgressBar progressBar = new JProgressBar();
        // progressBar.
        // 设置进度的 最小值 和 最大值
//        progressBar.setMinimum(0);
//        progressBar.setMaximum(100);
//        panel.add(progressBar);
        JButton btnApply = new JButton("开始转换");
        btnPanel.add(btnApply);
        btnApply.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
               // progressBar.setIndeterminate(true);
                /* 获取所有被选中的文件 */
                ConvertFiles convertFiles = new ConvertFiles();
                getAllSelectFiles(convertFiles, stm);
                /* 调用clang进行转换 */
                List<String> hppFileList = convertFiles.getHppFileList();
                List<String> cppFileList = convertFiles.getCppFileList();
                if(hppFileList.size()+cppFileList.size()>0){
                    useClangToConvert(hppFileList, cppFileList);
                    /* 如果选中的文件是cpp/hpp 进行删除 */
                    deleteHppCpp(hppFileList, cppFileList);
                    //progressBar.setIndeterminate(false);
                    JOptionPane.showMessageDialog(null, "转换完成", "提示", 1);
                }else{
                    //progressBar.setIndeterminate(false);
                    JOptionPane.showMessageDialog(null, "未选择任何文件", "警告", 0);
                }


            }
        });
        JButton btnClose = new JButton("关闭");
        btnPanel.add(btnClose);
        btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });



        add(btnPanel, BorderLayout.SOUTH);

        setTitle("请选择要转换的文件");

        setModal(true);

        pack();

        setVisible(true);
    }

    private void deleteHppCpp(List<String> hppFileList, List<String> cppFileList) {
        for (String hppFileName : hppFileList) {
            deleteFile(hppFileName);
        }
        for (String cppFileName : cppFileList) {
            deleteFile(cppFileName);
        }
    }

    private void deleteFile(String fileName) {
        File file =new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    private void useClangToConvert(List<String> hppFileList, List<String> cppFileList) {
        List<String> allFileList = new ArrayList<>();
        allFileList.addAll(hppFileList);
        allFileList.addAll(cppFileList);
        int fileCount = allFileList.size();
        String[] sourcePathArray = new String[fileCount];
        String[] targetPathArray = new String[fileCount];
        for (int i = 0; i < fileCount; i++) {
            String fileName = allFileList.get(i);
            sourcePathArray[i] = fileName;
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (Objects.equals(suffixName, "hpp")) {
                targetPathArray[i] = fileName.substring(0, fileName.lastIndexOf(".")) + ".h";
            } else {
                targetPathArray[i] = fileName.substring(0, fileName.lastIndexOf(".")) + ".c";
            }
        }

//        String[] s = new String[1];
//        String[] t = new String[1];
//        s[0]="C:\\Users\\bondc\\Desktop\\empty\\CtrlEx\\include\\GYROOUTPUT.hpp";
//        t[0]="C:\\Users\\bondc\\Desktop\\ao.c";

        Convert2CUtil.CLibrary.INSTANCE.convertFiles(sourcePathArray, targetPathArray, fileCount);
    }


    private void getAllSelectFiles(ConvertFiles convertFiles, SourceTableModel stm) {
        List<SourceModel> sourceList = stm.getSourceList();
        for (SourceModel sourceModel : sourceList) {
            if (sourceModel.isSelect()) {
                String fileName = sourceModel.getFileName();
                String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                if (suffixName.equalsIgnoreCase("h")) {
                    /* 更改文件后缀名称为hpp,在存入list集合中 */
                    File hFile = new File(fileName);
                    if (hFile.exists()) {
                        String hppFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".hpp";
                        //hFile.renameTo(new File(fileName));
                        copyFile(fileName,hppFileName);
                        convertFiles.getHppFileList().add(hppFileName);
                    }

                } else if (suffixName.equalsIgnoreCase("cpp")) {
                    convertFiles.getCppFileList().add(fileName);
                } else if (suffixName.equalsIgnoreCase("hpp")) {
                    convertFiles.getHppFileList().add(fileName);
                }
            }
        }
    }

    private void copyFile(String sourcePath, String targetPath) {
        File pathFrom = new File(sourcePath);
        File pathTo = new File(targetPath);

        /* 如果目标路径不存在, 则创建文件夹  */
        if (pathTo.exists()) {
            pathTo.delete();
        }
        try {
            Files.copy(pathFrom.toPath(), pathTo.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}






