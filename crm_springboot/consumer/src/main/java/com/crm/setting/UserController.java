package com.crm.setting;

import com.alibaba.dubbo.config.annotation.Reference;
import com.crm.model.User;
import com.crm.service.settings.UserService;
import com.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-27-17:27
 */
@Controller
@RequestMapping("/settings/user")
public class UserController {
    @Reference(interfaceClass = UserService.class, version = "1.0", check = false)
    private UserService userService;

    @RequestMapping("/login.do")
    @ResponseBody
    /**登陆验证**/
    public Object login(String loginAct, String loginPwd, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 密码 md5
        loginPwd = MD5Util.getMD5(loginPwd);
        // 获取用户ip
        String ip = request.getRemoteAddr();
        try {
            // 查询用户信息
            User user = userService.login(loginAct, loginPwd, ip);
            // 写入session
            request.getSession().setAttribute("user", user);
            // 返回json
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            result.put("success", false);
            result.put("msg", msg);
        }
        return result;
    }
}
