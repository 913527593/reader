package com.reader.books.bll;

import com.reader.books.dal.UserDao;
import com.reader.books.db.DB;
import com.reader.books.entify.User;

import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */

public class UserBiz {

    public UserBiz() {

    }

    /**
     * 由于在Bmob的表_User中注册时，已经有防止用户名重复注册的措施
     * Bmob中的表_User与本地数据库user表存在一一对应关系
     * 所以在本地数据库保存数据时也不需要判断用户名是否已经存在
     * 而且在Bmob的表_User中无法获得密码，所以密码需要作为参数保存在本地数据库
     *
     * @param user
     * @param pass
     * @return
     */
    public long add(User user, String pass) {
        UserDao dao = new UserDao();
        long id = dao.add(user, pass);
        return id;
    }

    /**
     *
     * @param name
     * @return
     */
    public User query(String name) {
        UserDao dao = new UserDao();
        String selection = DB.User.Colunms.NAME + "=?";
        String[] selectionArgs = new String[]{name};
        List<User> list = dao.query(selection, selectionArgs);
        if (list.size() > 0) {
            User user = list.get(0);
            return user;
        }
        return null;
    }
}
