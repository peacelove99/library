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
 * 作者: 元昱鹏
 * 时间: 2023-02-13
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private LendDao lendDao = new LendDaoImpl();

    /**
     *  查询
     * @return
     */
    @Override
    public List<User> select() {
        //调用Dao层写好的方法即可
        return userDao.select();
    }

    /**
     *  添加
     * @param user
     */
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    /**
     *  修改
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     *  删除
     * @param id
     */
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    /**
     *  冻结
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
     *  用户充值
     * @param user
     * @param money
     * @return
     */
    @Override
    public User charge(User user, BigDecimal money) {
        //计算充值之后的余额
        BigDecimal sum = money.add(user.getMoney());
        //判断充值后余额是否大于0
        if (BigDecimal.ZERO.compareTo(sum) < 0) {
            //修改用户状态
            user.setStatus(Constant.USER_OK);
        }

        user.setMoney(sum);
        //更新用户
        userDao.update(user);

        //修改借阅文件中的用户数据
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
