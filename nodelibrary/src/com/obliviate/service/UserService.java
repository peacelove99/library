package com.obliviate.service;

import com.obliviate.bean.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-13
 */
public interface UserService {
    List<User> select();

    void add(User user);

    void update(User user);

    void delete(int id);

    void frozen(int id);

    List<User> selectUserToLend();

    User charge(User user, BigDecimal money);
}
