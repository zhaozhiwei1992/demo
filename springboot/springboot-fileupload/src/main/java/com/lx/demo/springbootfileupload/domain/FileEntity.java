package com.lx.demo.springbootfileupload.domain;

public class FileEntity {

    private static final long serialVersionUID = -2635051333519441881L;

    private String fileName;

    private String isFolder;

    private String realPath;

    private String documentType;

    private String documentSubType;

    private String projectId;


    private String nodeId;


    private String objectId;


    private String extend;


    private String fileLevel;


    private String parentFileId;


    private String fileNo;


    private String objectTableName;


    private String orgId;


    private String fileSize;

    public String getDocumentSubType() {
        return documentSubType;
    }

    public void setDocumentSubType(String documentSubType) {
        this.documentSubType = documentSubType;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    public String getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(String fileLevel) {
        this.fileLevel = fileLevel;
    }

    public String getParentFileId() {
        return parentFileId;
    }

    public void setParentFileId(String parentFileId) {
        this.parentFileId = parentFileId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getObjectTableName() {
        return objectTableName;
    }

    public void setObjectTableName(String objectTableName) {
        this.objectTableName = objectTableName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

}
