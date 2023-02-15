
package com.obliviate.service;

import com.obliviate.bean.Admin;


public interface AdminService {

    Admin get(String name);

    void save(Admin admin);

}
