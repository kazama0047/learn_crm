package com.crm.service.workbench;

import com.alibaba.dubbo.config.annotation.Service;
import com.crm.mapper.workbench.*;
import com.crm.model.*;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Kazama
 * @create 2021-03-09-16:23
 */
@Component
@Service(interfaceClass = ClueService.class,version = "1.0",timeout = 15000)
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    @Transactional
    public boolean save(Clue clue) {
        boolean flag=true;
        int count=clueMapper.save(clue);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Clue detail(String id) {
        Clue c=clueMapper.detail(id);
        return c;
    }

    @Override
    @Transactional
    public boolean unbund(String id) {
        boolean flag=true;
        int count=clueActivityRelationMapper.unbund(id);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean bund(String cid, String[] aids) {
        boolean flag=true;
        for(String aid:aids){
            ClueActivityRelation car=new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(aid);
            int count=clueActivityRelationMapper.bund(car);
            if(count!=1){
                flag=false;
            }
        }
        return flag;
    }

    @Override
    @Transactional
    // 线索id,交易信息,创建人(不建议将request引入)
    public boolean convert(String clueId, Tran t, String createBy) {
        boolean flag=true;
        String createTime= DateTimeUtil.getSysTime();
        Clue c=clueMapper.getById(clueId);
        String company=c.getCompany();
        // 查询该潜在客户的公司是否已经存在
        Customer cus=customerMapper.getCustomerByName(company);
        if(cus==null){
            // 公司客户不存在,创建,将线索中公司相关的信息进行迁移
            cus=new Customer();
            cus.setId(UUIDUtil.getUUID());
            // 公司地址
            cus.setAddress(c.getAddress());
            // 公司网站
            cus.setWebsite(c.getWebsite());
            // 公司座机
            cus.setPhone(c.getPhone());
            // 拥有者
            cus.setOwner(c.getOwner());
            // 下次联系时间
            cus.setNextContactTime(c.getNextContactTime());
            // 公司名称
            cus.setName(company);
            // 描述信息
            cus.setDescription(c.getDescription());
            cus.setCreateTime(createTime);
            cus.setCreateBy(createBy);
            cus.setContactSummary(c.getContactSummary());
            int count1=customerMapper.save(cus);
            if(count1!=1){
                flag=false;
            }
        }
        // 创建联系人信息,将线索的信息迁移至联系人
        Contacts con=new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(c.getSource());
        con.setOwner(c.getOwner());
        con.setNextContactTime(c.getNextContactTime());
        con.setMphone(c.getMphone());
        con.setJob(c.getJob());
        con.setFullname(c.getFullname());
        con.setEmail(c.getEmail());
        con.setDescription(c.getDescription());
        con.setCustomerId(cus.getId());
        con.setCreateBy(createBy);
        con.setCreateTime(createTime);
        con.setContactSummary(c.getContactSummary());
        con.setAppellation(c.getAppellation());
        con.setAddress(c.getAddress());
        int count2=contactsMapper.save(con);
        if(count2!=1){
            flag=false;
        }
        // 将线索内的备注信息,迁移至顾客备注和联系人备注中
        List<ClueRemark> clueRemarkList=clueRemarkMapper.getListByClueId(clueId);
        for(ClueRemark clueRemark:clueRemarkList){
            String noteContent = clueRemark.getNoteContent();
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(cus.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            int count3=customerRemarkMapper.save(customerRemark);
            if(count3!=1){
                flag=false;
            }
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4=contactsRemarkMapper.save(contactsRemark);
            if(count4!=1){
                flag=false;
            }
        }
        //与线索关联的活动,写入 联系人活动关联表中,联系人在上面已经创建
        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationMapper.getListByClueId(clueId);
        for(ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            String activityId=clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());
            int count5=contactsActivityRelationMapper.save(contactsActivityRelation);
            if(count5!=1){
                flag=false;
            }
        }
        // 如果转换时提交了交易表单,将交易表需要的其他信息写入
        if(t!=null){
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setDescription(c.getDescription());
            t.setCustomerId(cus.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(con.getId());
            int count6=tranMapper.save(t);
            if(count6!=1){
                flag=false;
            }
            // 创建交易历史记录
            TranHistory th = new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setCreateBy(createBy);
            th.setCreateTime(createTime);
            th.setExpectedDate(t.getExpectedDate());
            th.setMoney(t.getMoney());
            th.setStage(t.getStage());
            th.setTranId(t.getId());
            int count7=tranHistoryMapper.save(th);
            if(count7!=1){
                flag=false;
            }
        }
        // 删除线索备注
        for(ClueRemark clueRemark:clueRemarkList){
            int count8=clueRemarkMapper.delete(clueRemark);
            if(count8!=1){
                flag=false;
            }
        }
        // 删除线索活动关联
        for(ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            int count9=clueActivityRelationMapper.delete(clueActivityRelation);
            if(count9!=1){
                flag=false;
            }
        }
        // 删除线索
        int count10=clueMapper.delete(clueId);
        if(count10!=1){
            flag=false;
        }
        return flag;
    }
}
