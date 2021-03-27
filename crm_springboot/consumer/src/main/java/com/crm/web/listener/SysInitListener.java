package com.crm.web.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.crm.model.DicValue;
import com.crm.service.settings.DicService;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * @author Kazama
 * @create 2021-03-09-17:23
 */

@Component
public class SysInitListener implements ServletContextListener {
    @Reference(interfaceClass = DicService.class, version = "1.0", check = false)
    private DicService dicService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("数据字典监听器");
        // 全局作用域对象
        ServletContext application = event.getServletContext();
        // 数据字典集合
        Map<String, List<DicValue>> map = dicService.getAll();
        // 遍历集合,将 字典类型+list后缀 作为属性名 ,关联值的集合作为值保存到application
        Set<String> set = map.keySet();
        for (String key : set) {
            application.setAttribute(key, map.get(key));
        }
        Map<String, String> pMap = new HashMap<String, String>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = rb.getString(key);
            pMap.put(key, value);
        }
        application.setAttribute("pMap", pMap);
    }
}
