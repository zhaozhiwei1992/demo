package com.lx.demo.springjdbc.framework;

public class Rule {
    private static final long serialVersionUID = 1L;
    private int type;	//规则的类型
    private String property_name;
    private Object[] values;
    private int andOr = 201;

    public Rule(int paramInt, String paramString) {
        this.property_name = paramString;
        this.type = paramInt;
    }

    public Rule(int paramInt, String paramString,
                Object[] paramArrayOfObject) {
        this.property_name = paramString;
        this.values = paramArrayOfObject;
        this.type = paramInt;
    }

    public Rule setAndOr(int andOr){
        this.andOr = andOr;
        return this;
    }

    public int getAndOr(){
        return this.andOr;
    }

    public Object[] getValues() {
        return this.values;
    }

    public int getType() {
        return this.type;
    }

    public String getPropertyName() {
        return this.property_name;
    }
}
