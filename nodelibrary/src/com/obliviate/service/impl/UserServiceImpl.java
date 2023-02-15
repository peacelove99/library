package com.obliviate.service.impl;

import com.obliviate.bean.Constant;
import com.obliviate.bean.Lend;
import com.obliviate.bean.User;
import com.obliviate.dao.LendDao;
import com.obliviate.dao.UserDao;
import com.obliviate.dao.impl.LendDaoImpl;
import com.obliviate.dao.impl.UserDaoImpl;
import com.obliviate.service.UserService;

import java.math.BigDecimal;
import java.util.List;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-13
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private LendDao lendDao = new LendDaoImpl();

    /**
     *  ��ѯ
     * @return
     */
    @Override
    public List<User> select() {
        //����Dao��д�õķ�������
        return userDao.select();
    }

    /**
     *  ���
     * @param user
     */
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    /**
     *  �޸�
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     *  ɾ��
     * @param id
     */
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    /**
     *  ����
     * @param id
     */
    @Override
    public void frozen(int id) {
        userDao.frozen(id);
    }

    @Override
    public List<User> selectUserToLend() {
        return userDao.selectUserToLend();
    }

    /**
     *  �û���ֵ
     * @param user
     * @param money
     * @return
     */
    @Override
    public User charge(User user, BigDecimal money) {
        //�����ֵ֮������
        BigDecimal sum = money.add(user.getMoney());
        //�жϳ�ֵ������Ƿ����0
        if (BigDecimal.ZERO.compareTo(sum) < 0) {
            //�޸��û�״̬
            user.setStatus(Constant.USER_OK);
        }

        user.setMoney(sum);
        //�����û�
        userDao.update(user);

        //�޸Ľ����ļ��е��û�����
        List<Lend> lendList = lendDao.select(null);

        for (int i = 0; i < lendList.size(); i++) {
            Lend lend = lendList.get(i);
            if (lend.getUser().getId() == user.getId()) {
                lend.setUser(user);
                lendDao.update(lend);
                break;
            }
        }


        return user;
    }
}
