package com.obliviate.dao.impl;

import com.obliviate.bean.Book;
import com.obliviate.bean.PathConstant;
import com.obliviate.dao.ChartDao;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ����: Ԫ����
 * ʱ��: 2023-02-14
 */
public class ChartDaoImpl implements ChartDao {
    /**
     *  ͳ��ͼ����������
     * @return
     */
    @Override
    public Map<String, Integer> bookTypeCount() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH))) {
            //��ȡ����
            List<Book> list = (List<Book>)ois.readObject();
            //ʹ��stream�����з���ͳ��
            Map<String, List<Book>> collect = list.stream().collect(Collectors.groupingBy(Book::getType));
            //�������
            HashMap<String, Integer> map = new HashMap<>();
            Iterator<Map.Entry<String, List<Book>>> iterator = collect.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Book>> next = iterator.next();
                map.put(next.getKey(), next.getValue() == null ? 0 : next.getValue().size());
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}