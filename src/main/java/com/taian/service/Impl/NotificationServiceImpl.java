package com.taian.service.Impl;

import com.taian.entity.Notification;
import com.taian.mapper.NotificationMapper;
import com.taian.service.INotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements INotificationService {

}
