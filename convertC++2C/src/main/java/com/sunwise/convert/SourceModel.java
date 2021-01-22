package com.sunwise.convert;

/**
 * Class that is holding the model for each row
 **/
public class SourceModel {
    private boolean active;
    private String fileName;

    public SourceModel() {
        // No Code;
    }

    public SourceModel(boolean select, String fileName) {
        super();
        this.active = select;
        this.fileName = fileName;
    }

    public boolean isSelect() {
        return active;
    }

    public void setSelect(boolean select) {
        this.active = select;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "SourceModel{" +
                "active=" + active +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
