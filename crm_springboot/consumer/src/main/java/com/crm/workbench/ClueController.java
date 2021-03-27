package com.crm.workbench;

import com.alibaba.dubbo.config.annotation.Reference;
import com.crm.model.Clue;
import com.crm.model.Tran;
import com.crm.model.User;
import com.crm.service.settings.UserService;
import com.crm.service.workbench.ActivityService;
import com.crm.service.workbench.ClueService;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.Result;
import com.crm.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-28-1:13
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Reference(interfaceClass = UserService.class, version = "1.0", check = false)
    private UserService userService;
    @Reference(interfaceClass = ClueService.class, version = "1.0", check = false)
    private ClueService clueService;
    @Reference(interfaceClass = ActivityService.class, version = "1.0", check = false)
    private ActivityService activityService;

    /*获取管理者列表*/
    @RequestMapping("/getUserList.do")
    public @ResponseBody
    Object getUserList() {
        return userService.getUserList();
    }

    /*新增线索*/
    @RequestMapping("/save.do")
    public @ResponseBody
    Object save(Clue clue, HttpServletRequest request) {
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        clue.setCreateBy(createBy);
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag = clueService.save(clue);
        return Result.returnFlag(flag);
    }

    /*详细信息*/
    @RequestMapping("/detail.do")
    public ModelAndView detail(String id) {
        ModelAndView mv = new ModelAndView();
        Clue clue = clueService.detail(id);
        System.out.println(clue.getId()+""+clue.getFullname());
        mv.addObject("c", clue);
        mv.setViewName("workbench/clue/detail");
        return mv;
    }

    /*根据线索id,查询市场活动*/
    @RequestMapping("/getActivityList.do")
    public @ResponseBody
    Object getActivityList(String id) {
        return activityService.getActivityListByClueId(id);
    }

    /*解绑*/
    @RequestMapping("/unbund.do")
    public @ResponseBody
    Object unbund(String id) {
        boolean flag = clueService.unbund(id);
        return Result.returnFlag(flag);
    }

    /*市场活动模糊查询,排除已关联的*/
    @RequestMapping("/getActivityListByNameAndNotByClueId.do")
    public @ResponseBody
    Object getActivityListByNameAndNotByClueId(String aname, String clueId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("aname", aname);
        map.put("clueId", clueId);
        return activityService.getActivityByNameAndNotByClueId(map);
    }

    /*绑定*/
    @RequestMapping("/bund.do")
    public @ResponseBody
    Object bund(String cid, String[] aid) {
        boolean flag = clueService.bund(cid, aid);
        return Result.returnFlag(flag);
    }

    /*市场活动模糊查询*/
    @RequestMapping("/getActivityListByName.do")
    public @ResponseBody
    Object getActivityListByName(String aname) {
        return activityService.getActivityListByName(aname);
    }

    /*潜在客户(线索)转换为顾客,信息存入顾客和联系人*/
    @RequestMapping("/convert.do")
    public void convert(Tran tran, String flag, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        if ("form".equals(flag)) {
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setCreateBy(createBy);
        }
        boolean b = clueService.convert(tran.getId(), tran, createBy);
        if (b)
            response.sendRedirect("redirect:" + request.getContextPath() + "/workbench/clue/index.jsp");
    }
}
