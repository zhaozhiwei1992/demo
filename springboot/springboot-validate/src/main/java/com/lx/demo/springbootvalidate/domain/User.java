package com.lx.demo.springbootvalidate.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class User {

   @Max(value = 1000)
   private long id;

   @NotNull
   private String name;

   /**
    * 自定义校验规则
    */
   private String cardNum;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCardNum() {
      return cardNum;
   }

   public void setCardNum(String cardNum) {
      this.cardNum = cardNum;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", cardNum='" + cardNum + '\'' +
              '}';
   }
}
