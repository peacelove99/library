package com.obliviate.service;

import com.obliviate.bean.Book;

import java.util.List;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-14
 */
public interface BookService {
    List<Book> select(Book book);

    void add(Book book);

    void delete(int id);

    void update(Book book);
}
