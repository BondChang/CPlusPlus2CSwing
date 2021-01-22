package com.sunwise.convert;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SourceTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private List<SourceModel> sourceList = new ArrayList<SourceModel>();
    private String[] columnNamesList = {"选择", "文件名称"};

//    public SourceTableModel() {
//
//        this.sourceList = getSourceDOList(allCppList, allCppList);
//    }

    public SourceTableModel(List<String> allCppList, List<String> allHppList) {
        this.sourceList = getSourceDOList(allCppList,allHppList);
    }

    @Override
    public String getColumnName(int column) {
        return columnNamesList[column];
    }

    @Override
    public int getRowCount() {
        return sourceList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNamesList.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ((columnIndex == 0) ? Boolean.class : String.class);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return ((columnIndex == 0) ? true : false);
    }

    /**
     * *Important:** Here when ever user clicks on the column one then other column values should be made false. Similarly vice-versa is also true.
     **/
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        SourceModel model = (SourceModel) sourceList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                if (model.isSelect()) {
                    model.setSelect(false);
                } else {
                    model.setSelect(true);
                }
                fireTableRowsUpdated(0, getRowCount() - 1);
                break;
            case 1:
                model.setFileName((String) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SourceModel source = sourceList.get(rowIndex);
//SourceModel source = getSourceDOList().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return source.isSelect();
            case 1:
                return source.getFileName();
            default:
                return null;
        }
    }

    /**
     * List for populating the table.
     *
     * @return list of sourceDO's.
     * @param cppList
     * @param hppList
     */
    private List<SourceModel> getSourceDOList(List<String> cppList, List<String> hppList) {
        List<SourceModel> tempSourceList = new ArrayList<SourceModel>();
        List<String> allFileList=new ArrayList();
        allFileList.addAll(cppList);
        allFileList.addAll(hppList);
        for(String fileName:allFileList){
            SourceModel source = new SourceModel();
            source.setSelect(false);
            source.setFileName(fileName);
            tempSourceList.add(source);
        }

        return tempSourceList;
    }

    public List<SourceModel> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<SourceModel> sourceList) {
        this.sourceList = sourceList;
    }
}
