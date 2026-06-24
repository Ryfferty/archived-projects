package com.xspaceagi.system.spec.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SuperTableColumn implements Serializable {

    /**
     * 序号
     */
    private int serialNumber;

    /**
     * 列的标题
     */
    private String label;
    /**
     * 列字段名称
     */
    private String name;
    /**
     * 是否开启列排序
     */
    private boolean sortable;
    /**
     * 列提示
     */
    private String tips;
    /**
     * 列扩展信息
     */
    private ColumnExt ext;


    private List<SuperTableColumn> children = Collections.emptyList();


    public SuperTableColumn(String label, String name, boolean sortable) {
        this.label = label;
        this.name = name;
        this.sortable = sortable;
    }

    public SuperTableColumn(String label, String name) {
        this.label = label;
        this.name = name;
        this.sortable = false;
    }


    public SuperTableColumn(int serialNumber, String label, String name) {
        this.serialNumber = serialNumber;
        this.label = label;
        this.name = name;
        this.sortable = false;
    }

    public SuperTableColumn(String label, String name, boolean sortable, ColumnExt ext) {
        this.label = label;
        this.name = name;
        this.sortable = sortable;
        this.ext = ext;
    }

    public SuperTableColumn(int serialNumber, String label, String name, boolean sortable, ColumnExt ext) {
        this.serialNumber = serialNumber;
        this.label = label;
        this.name = name;
        this.sortable = sortable;
        this.ext = ext;
    }

    public SuperTableColumn(String label, String name, ColumnExt ext) {
        this.label = label;
        this.name = name;
        this.sortable = false;
        this.ext = ext;
    }

    public SuperTableColumn(int serialNumber, String label, String name, ColumnExt ext) {
        this.serialNumber = serialNumber;
        this.label = label;
        this.name = name;
        this.sortable = false;
        this.ext = ext;
    }

    public SuperTableColumn() {

    }

}
