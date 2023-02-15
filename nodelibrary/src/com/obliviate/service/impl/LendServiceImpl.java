package com.obliviate.service.impl;

import com.obliviate.bean.Book;
import com.obliviate.bean.Constant;
import com.obliviate.bean.Lend;
import com.obliviate.bean.User;
import com.obliviate.dao.BookDao;
import com.obliviate.dao.LendDao;
import com.obliviate.dao.UserDao;
import com.obliviate.dao.impl.BookDaoImpl;
import com.obliviate.dao.impl.LendDaoImpl;
import com.obliviate.dao.impl.UserDaoImpl;
import com.obliviate.service.LendService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-14
 */
public class LendServiceImpl implements LendService {

    private LendDao lendDao = new LendDaoImpl();

    private BookDao bookDao = new BookDaoImpl();

    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<Lend> select(Lend lend) {
        return lendDao.select(lend);
    }

    /**
     * ��ӽ�������
     * @param bookId
     * @param userId
     */
    @Override
    public void add(int bookId,int userId) {
        //��ѯͼ������
        Book paramBook = new Book();
        paramBook.setId(bookId);
        List<Book> bookList = bookDao.select(paramBook);

        //���ݱ�Ų�ѯ�û�����
        User paramUser = new User();
        paramUser.setId(userId);
        List<User> userList = userDao.select(paramUser);

        Lend lend = new Lend();
        //���ɱ��
        lend.setId(UUID.randomUUID().toString());

        //��ͼ�鸳ֵ
        Book book = bookList.get(0);
        book.setStatus(Constant.STATUS_LEND);//��״̬�ĳɳ���
        lend.setBook(book);

        //���û���ֵ
        User user = userList.get(0);
        user.setLend(true);//�޸Ľ���״̬
        lend.setUser(user);

        lend.setStatus(Constant.STATUS_LEND);
        //���ó������ں͹黹����
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.plusDays(30));

        //�޸�ͼ��
        bookDao.update(book);
        //�޸��û�
        userDao.update(user);
        //��ӳ�������
        lendDao.add(lend);
    }

    /**
     *  ����
     * @param lend
     * @return
     */
    @Override
    public List<Lend> returnBook(Lend lend) {

        //��ȡ�û������ͼ�����
        Book book = lend.getBook();
        User user = lend.getUser();
        //�޸�״̬
        book.setStatus(Constant.STATUS_STORAGE);
        user.setLend(false);

        userDao.update(user);
        bookDao.update(book);

        //ɾ��lend����
        lendDao.delete(lend.getId());

        return lendDao.select(null);
    }

    @Override
    public void update(Lend lend) {
        lendDao.update(lend);
    }
}
