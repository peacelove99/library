package com.obliviate.service;

import com.obliviate.bean.Lend;

import java.util.List;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-14
 */
public interface LendService {
    List<Lend> select(Lend lend);

    void add(int bookId,int userId);

    List<Lend> returnBook(Lend lend);

    void update(Lend lend);
}
