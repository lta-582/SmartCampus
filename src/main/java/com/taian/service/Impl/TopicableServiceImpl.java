package com.taian.service.Impl;

import com.taian.entity.Topicable;
import com.taian.mapper.TopicableMapper;
import com.taian.service.ITopicableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TopicableServiceImpl extends ServiceImpl<TopicableMapper, Topicable> implements ITopicableService {

}
