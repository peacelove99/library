package com.obliviate.service.impl;

import com.obliviate.bean.Book;
import com.obliviate.dao.BookDao;
import com.obliviate.dao.impl.BookDaoImpl;
import com.obliviate.service.BookService;

import java.util.List;

/**
 * 作者: 元昱鹏
 * 时间: 2023-02-14
 */
public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    /**
     *  查询
     * @param book
     * @return
     */
    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }

    /**
     *  添加
     * @param book
     */
    @Override
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     *  删除
     * @param id
     */
    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

}
