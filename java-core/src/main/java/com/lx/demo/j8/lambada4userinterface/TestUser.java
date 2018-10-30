package com.lx.demo.j8.lambada4userinterface;

public class TestUser {
    public static void main(String[] args) {
        User user2 = new UserDao().savd(new IUser() {
            @Override
            public User getUserByName(String name) {
                User user = new User();
                user.setName(name);
                return user;
            }
        });
        System.out.println(user2);

        // 匿名类 lambada实现
        User user = new UserDao().savd(name -> {
            User user1 = new User();
            user1.setName(name);
            return user1;
        });
        System.out.println(user);

        // :: 找到具体实现这里很怪。. 这种方式 需要有实现，并且返回值与函数接口返回值一致即可  因吹死听
        User savd = new UserDao().savd(User::getUserByName);
        System.out.println(savd);
    }
}
