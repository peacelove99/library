package com.obliviate.service.impl;

import com.obliviate.bean.Book;
import com.obliviate.dao.BookDao;
import com.obliviate.dao.impl.BookDaoImpl;
import com.obliviate.service.BookService;

import java.util.List;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-14
 */
public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    /**
     *  ��ѯ
     * @param book
     * @return
     */
    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }

    /**
     *  ���
     * @param book
     */
    @Override
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     *  ɾ��
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
