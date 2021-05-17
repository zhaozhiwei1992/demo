package com.example.springbootpolardb.domain;

import java.util.Date;

public class BpmnTemplateDef {
    public static final Integer DEPLOY = 0;
    public static final Integer UN_DEPLOY = 1;
    public static final Integer DISABLE = 0;
    public static final Integer ENABLE = 1;
    public static final Integer FORBIDDEN = 2;
    private String id;
    private String name;
    private String category;
    private Integer version;
    private String createBy;
    private String modifyBy;
    private Date createTime;
    private String createTimeStr;
    private Date modifyTime;
    private String modifyTimeStr;
    private Integer deployState;
    private String deployStateStr;
    private Integer versionState;
    private String versionStateStr;
    private String deploymentId;
    private byte[] contentBytes;
    private String contentBytesStr;
    private Integer initNum;
    private String canvasWidth;
    private String canvasHeight;
    private String tableIds;
    private String tableNames;

    public BpmnTemplateDef() {
    }

    public String getTableIds() {
        return this.tableIds;
    }

    public void setTableIds(String tableIds) {
        this.tableIds = tableIds;
    }

    public String getTableNames() {
        return this.tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }

    public String getCanvasWidth() {
        return this.canvasWidth;
    }

    public void setCanvasWidth(String canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public String getCanvasHeight() {
        return this.canvasHeight;
    }

    public void setCanvasHeight(String canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDeployState() {
        return this.deployState;
    }

    public void setDeployState(Integer deployState) {
        this.deployState = deployState;
    }

    public Integer getVersionState() {
        return this.versionState;
    }

    public void setVersionState(Integer versionState) {
        this.versionState = versionState;
    }

    public String getModifyBy() {
        return this.modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getDeployStateStr() {
        return this.deployStateStr;
    }

    public void setDeployStateStr(String deployStateStr) {
        this.deployStateStr = deployStateStr;
    }

    public String getVersionStateStr() {
        return this.versionStateStr;
    }

    public void setVersionStateStr(String versionStateStr) {
        this.versionStateStr = versionStateStr;
    }

    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getModifyTimeStr() {
        return this.modifyTimeStr;
    }

    public void setModifyTimeStr(String modifyTimeStr) {
        this.modifyTimeStr = modifyTimeStr;
    }

    public Integer getInitNum() {
        return this.initNum;
    }

    public void setInitNum(Integer initNum) {
        this.initNum = initNum;
    }

    public byte[] getContentBytes() {
        return this.contentBytes;
    }

    public void setContentBytes(byte[] contentBytes) {
        this.contentBytes = contentBytes;
    }

    public String getContentBytesStr() {
        return this.contentBytesStr;
    }

    public void setContentBytesStr(String contentBytesStr) {
        this.contentBytesStr = contentBytesStr;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}
