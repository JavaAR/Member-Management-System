package cn.DeepBlue.dao;

import cn.DeepBlue.pojo.Subscribestatus;
import cn.DeepBlue.utils.MyMapper;

import java.util.List;

public interface SubscribestatusMapper extends MyMapper<Subscribestatus> {

    List<Subscribestatus> subscribestatusMapper();
}