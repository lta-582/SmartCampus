package com.taian.service.Impl;

import com.taian.entity.Inbox;
import com.taian.mapper.InboxMapper;
import com.taian.service.IInboxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class InboxServiceImpl extends ServiceImpl<InboxMapper, Inbox> implements IInboxService {

}
