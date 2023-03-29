package com.taian.service.Impl;

import com.taian.entity.Vote;
import com.taian.mapper.VoteMapper;
import com.taian.service.IVoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements IVoteService {

}
