package edu.uestc.service;

import edu.uestc.po.User;

/**
 * Created by zw on 2019/12/14.
 */
public interface UserService {

    User checkUser(String username, String password);
}
