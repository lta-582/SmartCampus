package com.taian.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taian.entity.Comment;
import com.taian.entity.CommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select comment.comment_id, comment.commentable_id , comment.commentable_type, comment.user_id, comment.content, comment.create_time, user.nick as nick, user.avatar as avatar\n" +
            "from comment INNER JOIN user ON comment.user_id = user.user_id \n" +
            "${ew.customSqlSegment}")
    IPage<CommentVO> getCommentByOpr(IPage<CommentVO> page, @Param("ew") Wrapper wrapper);
//    Select("(select comment.comment_id, comment.commentable_id, comment.commentable_type, comment.user_id, comment.content, comment.create_time, user.nick as nick, user.avatar as avatar\n" +
//            "from comment, user \n"+
//            "where comment.user_id=user.user_id) as a\n" +
//            "${ew.customSqlSegment}")
//    IPage<CommentVO> getCommentByOpr(IPage<CommentVO> page, @Param("ew") Wrapper wrapper);

}
