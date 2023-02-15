package com.obliviate.dao;

import com.obliviate.bean.Book;

import java.util.List;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-14
 */
public interface BookDao {
    List<Book> select(Book book);

    void add(Book book);

    void delete(int id);

    void update(Book book);
}
