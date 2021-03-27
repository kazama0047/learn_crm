package com.crm.workbench;

import com.alibaba.dubbo.config.annotation.Reference;
import com.crm.exception.ActivityException;
import com.crm.model.Activity;
import com.crm.model.ActivityRemark;
import com.crm.model.User;
import com.crm.service.settings.UserService;
import com.crm.service.workbench.ActivityService;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.Result;
import com.crm.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-27-23:10
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Reference(interfaceClass = UserService.class, version = "1.0", check = false)
    private UserService userService;
    @Reference(interfaceClass = ActivityService.class, version = "1.0", check = false)
    private ActivityService activityService;

    /*获取下拉列表*/
    @RequestMapping("/getUserList.do")
    public @ResponseBody
    Object getUserList() {
        List<User> users = userService.getUserList();
        return users;
    }

    /*市场活动添加*/
    @RequestMapping("/save.do")
    public @ResponseBody
    Object save(Activity activity, HttpServletRequest request) {
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        activity.setId(id);
        activity.setCreateTime(createTime);
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        activity.setCreateBy(createBy);
        System.out.println(activity);
        try {
            activityService.save(activity);
            return Result.returnFlag(true);
        } catch (ActivityException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> result = Result.returnObject();
            result.put("success", false);
            result.put("msg", msg);
            return result;
        }
    }

    /*分页功能*/
    @RequestMapping("/pageList.do")
    public @ResponseBody
    Object pageList(Activity activity, String pageNo, String pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", activity.getName());
        map.put("owner", activity.getOwner());
        map.put("startDate", activity.getStartDate());
        map.put("endDate", activity.getEndDate());
        int pageNoInt = Integer.valueOf(pageNo);
        int pageSizeInt = Integer.valueOf(pageSize);
        int skipCount = (pageNoInt - 1) * pageSizeInt;
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSizeInt);
        return activityService.pageList(map);
    }

    /*删除活动*/
    @RequestMapping("/delete.do")
    public @ResponseBody
    Object delete(String[] id) {
        boolean flag = activityService.delete(id);
        return Result.returnFlag(flag);
    }

    /*用户活动关联表*/
    @RequestMapping("getUserListAndActivity.do")
    public @ResponseBody
    Object getUserListAndActivity(String id) {
        return activityService.getUserListAndActivity(id);
    }

    /*修改活动*/
    @RequestMapping("/update.do")
    public @ResponseBody
    Object update(Activity activity, HttpServletRequest request) {
        // 写入修改时间
        activity.setEditTime(DateTimeUtil.getSysTime());
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        activity.setEditBy(editBy);
        try {
            activityService.update(activity);
            return Result.returnFlag(true);
        } catch (ActivityException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = Result.returnObject();
            map.put("success", false);
            map.put("msg", msg);
            return map;
        }
    }

    /*详细信息*/
    @RequestMapping("/detail.do")
    public ModelAndView detail(String id) {
        ModelAndView mv = new ModelAndView();
        Activity activity = activityService.detail(id);
        mv.addObject("a", activity);
        mv.setViewName("workbench/activity/detail");
        return mv;
    }

    /*展示备注*/
    @RequestMapping("/getRemarkListByAid.do")
    public @ResponseBody
    Object getRemarkListByAid(String id) {
        return activityService.getRemarkListByAid(id);
    }

    /*删除备注*/
    @RequestMapping("/deleteRemark.do")
    public @ResponseBody
    Object deleteRemark(String id) {
        boolean flag = activityService.deleteRemark(id);
        return Result.returnFlag(flag);
    }

    /*新增备注*/
    @RequestMapping("/saveRemark.do")
    public @ResponseBody
    Object saveRemark(ActivityRemark activityRemark, HttpServletRequest request) {
        activityRemark.setId(UUIDUtil.getUUID());
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        activityRemark.setCreateBy(createBy);
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");
        ;
        boolean flag = activityService.saveRemark(activityRemark);
        if (flag) {
            Map<String, Object> map = Result.returnObject();
            map.put("success", flag);
            map.put("ar", activityRemark);
            return map;
        } else {
            return Result.returnFlag(flag);
        }
    }

    /*修改备注*/
    @RequestMapping("/updateRemark.do")
    public @ResponseBody
    Object updateRemark(ActivityRemark activityRemark, HttpServletRequest request) {
        activityRemark.setEditFlag("1");//修改标记
        activityRemark.setEditTime(DateTimeUtil.getSysTime());//修改时间
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        activityRemark.setEditBy(editBy);//修改人
        boolean flag = activityService.updateRemark(activityRemark);
        if (flag) {
            Map<String, Object> map = Result.returnObject();
            map.put("ar", activityRemark);
            map.put("success", true);
            return map;
        } else {
            return Result.returnFlag(flag);
        }
    }
}
