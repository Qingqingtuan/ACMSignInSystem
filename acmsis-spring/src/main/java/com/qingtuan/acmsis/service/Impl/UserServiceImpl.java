package com.qingtuan.acmsis.service.Impl;

import com.qingtuan.acmsis.entity.JsonResult;
import com.qingtuan.acmsis.entity.User;
import com.qingtuan.acmsis.entity.UserStatus;
import com.qingtuan.acmsis.mapper.UserMapper;
import com.qingtuan.acmsis.mapper.UserStatusMapper;
import com.qingtuan.acmsis.service.UserService;
import com.qingtuan.acmsis.service.UserStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.qingtuan.acmsis.constant.ErorrMessage.*;

/**
 * @Author: Qingtuan
 * @Date: 2022/10/13 19:26
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserStatusMapper userStatusMapper;

    @Override
    public JsonResult GetAllUser() {
        List<User> data;
        try {
            data = userMapper.GetAllUser();
        } catch (DataAccessException e) {
            return new JsonResult<>(false, GetAllUserFail.toString(), e.getMessage());
        }
        return new JsonResult<>(true, null, data);
    }

    @Override
    public User GetUser(Integer Id) {
        return userMapper.GetUser(Id).get(0);
    }

    @Override
    public User GetId(String rule) throws DataAccessException {
        return userMapper.GetId(rule).get(0);
    }

    @Override
    public JsonResult AddUser(User user) {
        try {
            if (userMapper.AddUser(user) == 0) return new JsonResult(false, AddUserFail.toString());
        } catch (DataAccessException e) {
            return new JsonResult(false, AddUserFail.toString(), e.getMessage());
        }
        UserStatus userStatus;
        try {
            userStatus = new UserStatus(GetId(user.getStuCardNum()).getId(), false);
            userStatusMapper.AddUserStatus(userStatus);
        } catch (DataAccessException e) {
            return new JsonResult(false, AddUserStatusFail.toString(), e.getMessage());
        }
        return new JsonResult(true, null);
    }

    @Override
    public JsonResult DeleteUser(User user) {
        try {
            userMapper.DeleteUser(user);
        } catch (DataAccessException e) {
            return new JsonResult(false, DeleteUserFail.toString(), e.getMessage());
        }
        return new JsonResult(true, null);
    }

    @Override
    public JsonResult UpdateUser(User user) {
        try {
            userMapper.UpdateUser(user);
        } catch (DataAccessException e) {
            return new JsonResult(false, UpdateUserFail.toString(), e.getMessage());
        }
        return new JsonResult(true, null);
    }
}
