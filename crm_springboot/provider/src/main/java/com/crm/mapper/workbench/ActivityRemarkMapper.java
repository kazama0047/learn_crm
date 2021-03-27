package com.crm.mapper.workbench;

import com.crm.model.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String id);

    int deleteById(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}