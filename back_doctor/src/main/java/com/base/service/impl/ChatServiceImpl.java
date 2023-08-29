package com.base.service.impl;

import com.base.bean.Chat;
import com.base.mapper.ChatMapper;
import com.base.service.IChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天 服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-08-11
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {

}
