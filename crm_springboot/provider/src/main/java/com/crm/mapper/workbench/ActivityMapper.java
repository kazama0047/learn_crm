package com.crm.mapper.workbench;

import com.crm.model.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int save(Activity a);

    /**根据创建活动的时间排序 降序**/
    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}