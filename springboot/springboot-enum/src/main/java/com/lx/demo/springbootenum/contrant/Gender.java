package com.lx.demo.springbootenum.contrant;

public enum Gender {
    FEMALE(0,"男"),
    MALE(1,"女");
    private int status;
    private String text;
 
    Gender(int status,String text){
        this.status=status;
        this.text=text;
    }
 
    public static Gender get(int v) {
        String str = String.valueOf(v);
        return get(str);
    }
 
    public static Gender get(String str) {
        for (Gender e : values()) {
            if(e.toString().equals(str)) {
                return e;
            }
        }
        return null;
    }

    public static Gender getByStatus(int status){
        for (Gender e : values()) {
            if(e.getStatus() == status) {
                return e;
            }
        }
        return null;
    }


    public int getStatus() {
        return status;
    }
 
    public String getText() {
        return text;
    }
}
