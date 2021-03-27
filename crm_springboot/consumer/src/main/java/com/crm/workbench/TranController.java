package com.crm.workbench;

import com.alibaba.dubbo.config.annotation.Reference;
import com.crm.model.Tran;
import com.crm.model.TranHistory;
import com.crm.model.User;
import com.crm.service.settings.UserService;
import com.crm.service.workbench.ActivityService;
import com.crm.service.workbench.ContactsService;
import com.crm.service.workbench.CustomerService;
import com.crm.service.workbench.TranService;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.Result;
import com.crm.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-28-1:49
 */
@Controller
@RequestMapping("/workbench/transaction")
public class TranController {

    @Reference(interfaceClass = UserService.class, version = "1.0", check = false)
    private UserService userService;
    @Reference(interfaceClass = ActivityService.class, version = "1.0", check = false)
    private ActivityService activityService;
    @Reference(interfaceClass = ContactsService.class, version = "1.0", check = false)
    private ContactsService contactsService;
    @Reference(interfaceClass = CustomerService.class, version = "1.0", check = false)
    private CustomerService customerService;
    @Reference(interfaceClass = TranService.class, version = "1.0", check = false)
    private TranService tranService;

    /*转发用户列表*/
    @RequestMapping("/add.do")
    public String add(Model model) {
        List<User> uList = userService.getUserList();
        model.addAttribute("uList", uList);
        return "workbench/transaction/save";
    }

    /*搜索活动*/
    @RequestMapping("/searchActivityByName.do")
    public @ResponseBody
    Object searchActivityByName(String name) {
        return activityService.getActivityListByName(name);
    }

    /*模糊查询联系人列表*/
    @RequestMapping("/searchContactsListByName.do")
    public @ResponseBody
    Object searchContactsListByName(String name) {
        return contactsService.getContactsListByName(name);
    }

    /*模糊查询 快捷输入*/
    @RequestMapping("/getCustomerName.do")
    public @ResponseBody
    Object getCustomerName(String name) {
        return customerService.getCustomerName(name);
    }

    /*交易保存*/
    @RequestMapping("/save.do")
    public void save(Tran tran, String customerName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        tran.setId(UUIDUtil.getUUID());
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        tran.setCreateBy(createBy);
        tran.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag = tranService.save(tran, customerName);
        if (flag)
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
    }

    /*详细信息*/
    @RequestMapping("/detail.do")
    public ModelAndView detail(String id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Tran tran = tranService.detail(id);
        String stage = tran.getStage();
        // 获取保存在全局作用域中的 阶段 和 可能性值的映射
        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        // 阶段对应的值
        String possibility = pMap.get(stage);
        tran.setPossibility(possibility);
        mv.addObject("t", tran);
        mv.setViewName("workbench/transaction/detail");
        return mv;
    }

    /*展示交易历史*/
    @RequestMapping("/getHistoryByTranId.do")
    public @ResponseBody
    Object getHistoryByTranId(String tranId, HttpServletRequest request) {
        List<TranHistory> list = tranService.getHistoryListByTranId(tranId);
        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        for (TranHistory th : list) {
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);
        }
        return list;
    }

    /*改变交易阶段*/
    @RequestMapping("/changeStage.do")
    public @ResponseBody
    Object changeStage(Tran tran, HttpServletRequest request) {
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        tran.setEditBy(editBy);
        tran.setEditTime(DateTimeUtil.getSysTime());
        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        tran.setPossibility(pMap.get(tran.getStage()));
        boolean flag = tranService.changeStage(tran);
        Map<String, Object> map = Result.returnObject();
        map.put("success", flag);
        map.put("t", tran);
        return map;
    }
    /*每个阶段的交易个数*/
    @RequestMapping("/getCharts.do")
    public @ResponseBody
    Object getCharts(){
        return tranService.getCharts();
    }
}
