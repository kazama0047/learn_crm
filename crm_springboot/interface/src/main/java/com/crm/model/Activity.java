package com.crm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kazama
 * @create 2021-03-05-16:35
 */
@Data
public class Activity implements Serializable {
    private String id;  // 主键
    private String owner;  // 所有者 外键 关联tbl_user
    private String name;  // 市场活动名称
    private String startDate;  // 开始日期 年月日
    private String endDate;  // 结束日期 年月日
    private String cost;  // 成本
    private String description;  // 描述
    private String createTime;  // 创建时间
    private String createBy;  // 创建人
    private String editTime;  // 修改时间
    private String editBy;  // 修改人

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", cost='" + cost + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                '}';
    }
}
