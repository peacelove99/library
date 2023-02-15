package com.obliviate.service.impl;

import com.obliviate.dao.ChartDao;
import com.obliviate.dao.impl.ChartDaoImpl;
import com.obliviate.service.ChartService;

import java.util.Map;

/**
 * 作者: 元昱鹏
 * 时间: 2023-02-14
 */
public class ChartServiceImpl implements ChartService {

    private ChartDao chartDao = new ChartDaoImpl();

    @Override
    public Map<String, Integer> bookTypeCount() {
        return chartDao.bookTypeCount();
    }
}
