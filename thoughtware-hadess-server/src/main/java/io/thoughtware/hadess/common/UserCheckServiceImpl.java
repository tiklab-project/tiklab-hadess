package io.thoughtware.hadess.common;

import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.eam.common.model.EamTicket;
import io.thoughtware.eam.passport.user.model.UserPassport;
import io.thoughtware.eam.passport.user.service.UserPassportService;
import io.thoughtware.user.user.model.User;
import io.thoughtware.user.user.model.UserQuery;
import io.thoughtware.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 用户校验
* */

@Service
public class UserCheckServiceImpl implements UserCheckService {

    @Autowired
    UserService userService;

    @Autowired
    UserPassportService userPassportService;

    @Override
    public String mavenUserCheck(String userData) {
        String userName;
        if (("hadess".equals(userData))){
            //maven制品库里面上传用户校验用户信息默认用的xpack
            String loginId = LoginContext.getLoginId();
            userName = userService.findUser(loginId).getName();
        }else {
            String[]  userObject=userData.split(":");
            userName = userObject[0];
            String password = userObject[1];

            UserPassport userPassport = new UserPassport();
            userPassport.setAccount(userName);
            userPassport.setPassword(password);
            userPassport.setDirId("1");
            userPassportService.validLogin(userPassport);
        }
        return userName;
    }

    @Override
    public String npmUserCheck(String userName,String password) {
        UserPassport userPassport = new UserPassport();
        userPassport.setAccount(userName);
        userPassport.setPassword(password);
        userPassport.setDirId("1");
        EamTicket eamTicket = userPassportService.login(userPassport);
        return eamTicket.getTicket();
    }

    @Override
    public List<User> npmUserCheckByName(String userName) {
        UserQuery userQuery = new UserQuery();
        userQuery.setName(userName);
        List<User> userList = userService.findUserList(userQuery);
        return userList;
    }

    @Override
    public void dockerUserCheck(String userData) {
        String[] split = userData.split(":");
        String userName = split[0];
        String password = split[1];

        UserPassport userPassport = new UserPassport();
        userPassport.setAccount(userName);
        userPassport.setPassword(password);
        userPassport.setDirId("1");
         userPassportService.validLogin(userPassport);
    }

    @Override
    public String genericUserCheck(String userData) {
        String[]  userObject=userData.split(":");
        String userName = userObject[0];
        String password = userObject[1];

        //generic制品库里面上传 制品
        if (("xpackhand").equals(password)){
            return userName;
        }

        UserPassport userPassport = new UserPassport();
        userPassport.setAccount(userName);
        userPassport.setPassword(password);
        userPassport.setDirId("1");
        userPassportService.validLogin(userPassport);
        return userName;
    }
}
