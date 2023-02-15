package com.obliviate.dao;

import com.obliviate.bean.User;

import java.util.List;

/**
 * 作者: 元昱鹏
 * 时间: 2023-02-13
 */
public interface UserDao {
    List<User> select();

    List<User> select(User user);

    void add(User user);

    void update(User user);

    void delete(int id);

    void frozen(int id);

    List<User> selectUserToLend();
}
