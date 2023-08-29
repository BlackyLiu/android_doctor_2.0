package com.base.mapper;

import com.base.bean.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 聊天 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-08-11
 */
public interface ChatMapper extends BaseMapper<Chat> {

    //找到通信双方唯一的记录
    //确定通信双方的 信息
    @Select("select  id, content, sendName, recvName, sendId, recvId, create_time as createTime from chat where  create_time > DATE_SUB(CURDATE(),INTERVAL 1 DAY) and (recvId = #{id} or sendId = #{id})  group by recvId,sendId order by create_time desc")
    List<Chat> queryTxYh(Long id);

    //查找通信双方唯一的一天之内的记录
    @Select("select id, content, sendName, recvName, sendId, recvId, create_time as createTime from chat where  create_time > DATE_SUB(CURDATE(),INTERVAL 1 DAY) and ((recvId = #{myId} and sendId = #{hisId}) or (recvId = #{hisId} and sendId = #{myId})) ")
    List<Chat> queryYxQb(@Param("myId") Long myId, @Param("hisId") Long hisId);
}
