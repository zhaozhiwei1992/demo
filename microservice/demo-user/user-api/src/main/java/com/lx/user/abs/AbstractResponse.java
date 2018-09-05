package com.lx.user.abs;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 赵志伟
 * @ClassName: AbstractResponse
 * @description [描述该类的功能]
 * @create 2018-07-06 下午2:20
 **/
@Data
public abstract class AbstractResponse implements Serializable{

    private static final long serialVersionUID = 5704430076796818950L;
    private String code;
    private String msg;
}
