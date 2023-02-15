package com.obliviate.service;

import com.obliviate.bean.Book;

import java.util.List;

/**
 * 作者: 元昱鹏
 * 时间: 2023-02-14
 */
public interface BookService {
    List<Book> select(Book book);

    void add(Book book);

    void delete(int id);

    void update(Book book);
}
