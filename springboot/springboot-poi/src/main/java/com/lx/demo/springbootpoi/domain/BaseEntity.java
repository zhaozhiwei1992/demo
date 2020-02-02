package com.lx.demo.springbootpoi.domain;

import com.lx.demo.springbootpoi.annotation.PropertyName;

import java.io.Serializable;

/**
 * 通用基础实体类，所有的entity都要继续此类
 * @description
 *  
 * @author hyq
 */
public class BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5553487015252476905L;
	@PropertyName("ID")
	protected String id;

    @PropertyName("创建时间")
	//@JSONField
    private String createDateTime;
    
    @PropertyName("创建人")
    private String createUser;
    
    @PropertyName("最后修改时间")
    //@JSONField(name="lastUpdateTime") //这个注解的意思是json的名字与实体类的字段保持一致
    private String lastUpdateTime;
    
    @PropertyName("最后修改人")
    private String lastUpdateUser;
    
    /**
     * 用来区分数据权限
     */
    @PropertyName("登录用户的机构id")
    private String userOrgId;
    
    @PropertyName("登录用户的角色id")
    private String userRoleId;

    @PropertyName("是否删除:是,YES,否,NO")
    private String isDelete;
    
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    

    public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

	public String getUserOrgId() {
		return userOrgId;
	}

	public void setUserOrgId(String userOrgId) {
		this.userOrgId = userOrgId;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

    
    
}
