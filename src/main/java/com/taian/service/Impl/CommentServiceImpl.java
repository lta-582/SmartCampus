package com.taian.service.Impl;

import com.taian.entity.Comment;
import com.taian.mapper.CommentMapper;
import com.taian.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
