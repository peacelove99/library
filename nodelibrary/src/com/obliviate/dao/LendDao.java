package com.obliviate.dao;

import com.obliviate.bean.Lend;

import java.util.List;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-14
 */
public interface LendDao {
    List<Lend> select(Lend lend);

    void add(Lend lend);

    void delete(String id);

    void update(Lend lend);
}
