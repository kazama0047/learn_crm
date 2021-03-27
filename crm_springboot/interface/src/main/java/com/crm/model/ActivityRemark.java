package com.crm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kazama
 * @create 2021-03-05-16:36
 */
@Data
public class ActivityRemark implements Serializable {
    private String id;  //
    private String noteContent;  // 备注信息
    private String createTime;  //
    private String createBy;  //
    private String editTime;  //
    private String editBy;  //
    private String editFlag;  // 是否修改过的标记
    private String activityId;  //
}
