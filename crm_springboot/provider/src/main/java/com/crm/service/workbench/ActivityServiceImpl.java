package com.crm.service.workbench;

import com.alibaba.dubbo.config.annotation.Service;
import com.crm.exception.ActivityException;
import com.crm.mapper.settings.UserMapper;
import com.crm.mapper.workbench.ActivityMapper;
import com.crm.mapper.workbench.ActivityRemarkMapper;
import com.crm.model.Activity;
import com.crm.model.ActivityRemark;
import com.crm.model.User;
import com.crm.vo.PageinationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-05-16:42
 */
@Component
@Service(interfaceClass = ActivityService.class, version = "1.0", timeout = 15000)
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void save(Activity a) throws ActivityException {
        if (a.getStartDate().compareTo(a.getEndDate()) > 0) {
            throw new ActivityException("活动开始时间不能晚于结束时间");
        }
        int i = activityMapper.save(a);
        if (i != 1) {
            throw new ActivityException("活动添加失败");
        }
    }

    @Override
    public PageinationVO pageList(Map<String, Object> map) {
        int total = activityMapper.getTotalByCondition(map);
        List<Activity> dataList = activityMapper.getActivityListByCondition(map);
        PageinationVO<Activity> vo = new PageinationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    @Transactional
    public boolean delete(String[] ids) {
        boolean flag = true;
        // 查询需要删除的备注的数量
        int count1 = activityRemarkMapper.getCountByAids(ids);
        // 删除备注,返回受到影响的记录数
        int count2 = activityRemarkMapper.deleteByAids(ids);
        if (count1 != count2) {
            flag = false;
        }
        // 事件删除的活动记录数与输入的活动id数
        int count3 = activityMapper.delete(ids);
        if (count3 != ids.length) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        // 获取用户list
        List<User> userList = userMapper.getUserList();
        // 获取activity对象
        Activity activity = activityMapper.getById(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uList", userList);
        map.put("a", activity);
        return map;
    }

    @Override
    @Transactional
    public void update(Activity a) throws ActivityException {
        if (a.getStartDate().compareTo(a.getEndDate()) > 0) {
            throw new ActivityException("活动开始时间不能晚于结束时间");
        }
        int i = activityMapper.update(a);
        if (i != 1) {
            throw new ActivityException("活动修改失败");
        }
    }

    @Override
    public Activity detail(String id) {
        Activity a = activityMapper.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String id) {
        List<ActivityRemark> list = activityRemarkMapper.getRemarkListByAid(id);
        return list;
    }

    @Override
    @Transactional
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = activityRemarkMapper.deleteById(id);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;
        int count = activityRemarkMapper.saveRemark(ar);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;
        int count = activityRemarkMapper.updateRemark(ar);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        return activityMapper.getActivityListByClueId(clueId);
    }

    @Override
    public List<Activity> getActivityByNameAndNotByClueId(Map<String, String> map) {
        return activityMapper.getActivityListByNameAndNotByClueId(map);
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        return activityMapper.getActivityListByName(aname);
    }
}
