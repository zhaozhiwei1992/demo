package com.example.springbootactivity.activiti5.dto;

import java.io.Serializable;

public class Action implements Serializable {

    private static final long serialVersionUID = -1199490978099373215L;
    public final static String CREATE = "create";
    public final static String SENDAUDIT = "sendaudit";
    public final static String AUDIT = "audit";
    public final static String BACK = "back";
    public final static String UPDATE = "update";
    public final static String DELETE = "delete";
    public final static String OBSOLETE = "obsolete";


    /**
     * 操作类型
     */
    private String actionType;


    /**
     * 是否取消
     */
    private boolean cancel;

    /**
     * 是否退回
     */
    private boolean back;

    /**
     * 执行工作流的参数变量
     */
    private String variable;


    /**
     * 是否允许修改
     */
    private boolean modify;

    /**
     * 是否忽略额度控制警告
     */
    private boolean passwarn;

    /**
     * 是否忽略审核规则警告
     */
    private boolean passAuditWarn;


    /**
     * 构造函数
     *
     * @param actionType 操作类型
     */
    public Action(String actionType) {
        super();
        this.actionType = actionType;
    }

    /**
     * 构造函数
     *
     * @param actionType 操作类型
     * @param cancel     是否取消
     */
    public Action(String actionType, boolean cancel) {
        super();
        this.actionType = actionType;
        this.cancel = cancel;
    }

    /**
     * 构造函数
     *
     * @param actionType 操作类型
     * @param cancel     是否取消
     * @param variable   意见
     */
    public Action(String actionType, boolean cancel, String variable) {
        super();
        this.actionType = actionType;
        this.cancel = cancel;
        this.variable = variable;
    }


    /**
     * 返回增加操作对象
     *
     * @return 返回增加操作对象
     */
    public static Action Create() {
        return new Action(Action.CREATE);
    }

    /**
     * 返回送审操作对象
     *
     * @return 返回增加操作对象
     */
    public static Action SendAudit() {
        return new Action(Action.SENDAUDIT);
    }

    /**
     * 返回取消送审操作对象
     *
     * @return 返回取消增加操作对象
     */
    public static Action CancelSendAudit() {
        return new Action(Action.SENDAUDIT, true);
    }

    /**
     * 返回审核操作对象
     *
     * @return 返回审核操作对象
     */
    public static Action Audit() {
        return new Action(Action.AUDIT);
    }

    /**
     * 返回取消审核操作对象
     *
     * @return 返回取消审核操作对象
     */
    public static Action CancelAudit() {
        return new Action(Action.AUDIT, true);
    }


    /**
     * 返回退回操作对象
     *
     * @return 返回退回操作对象
     */
    public static Action Back() {
        return new Action(Action.BACK);
    }

    /**
     * 返回作废操作对象
     *
     * @return 返回作废操作对象
     */
    public static Action Obsolete() {
        return new Action(Action.OBSOLETE);
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isBack() {
        return this.back;
    }


    public void setBack(boolean back) {
        this.back = back;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean isPasswarn() {
        return passwarn;
    }

    public void setPasswarn(boolean passwarn) {
        this.passwarn = passwarn;
    }

    public boolean isPassAuditWarn() {
        return passwarn || passAuditWarn;
    }

    public void setPassAuditWarn(boolean passAuditWarn) {
        this.passAuditWarn = passAuditWarn;
    }

    @Override
    public String toString() {
        return "Action [actionType=" +
                actionType +
                ", cancel=" +
                cancel +
                "]";
    }
}