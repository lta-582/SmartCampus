package com.taian.service.Impl;

import com.taian.entity.Topic;
import com.taian.mapper.TopicMapper;
import com.taian.service.ITopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {

}
