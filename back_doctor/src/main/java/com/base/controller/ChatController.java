package com.base.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.mapper.ChatMapper;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import com.base.transfer.ResultBean;
import com.base.transfer.PageTo;
import com.base.service.IChatService;
import com.base.bean.Chat;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 聊天 模块
 * </p>
 */
@RestController
@RequestMapping("chat")
@Slf4j
public class ChatController {
        @Autowired
        private IChatService chatService;





        // 新增
        @PostMapping("/add")
        public ResultBean add( Chat chat) {
                chat.setCreateTime(new Date());
                boolean save =chatService.save(chat);
                log.info("调用创建接口:{},数据:{}",save,chat);
                return save?ResultBean.ok():ResultBean.error400();
        }

        // 更新
        @PostMapping("/update")
        public  ResultBean update( Chat chat){
                boolean update = chatService.updateById(chat);
                log.info("调用更新接口:{},数据:{}",update,chat);
                return update?ResultBean.ok():ResultBean.error400();
        }

        //删除
        @GetMapping("delete")
        @ResponseBody
        public ResultBean delete(Long id) {
                boolean del = chatService.removeById(id);
                log.info("调用删除接口:{},数据:{}",del,id);
                return del?ResultBean.ok():ResultBean.error400();
        }

    //多项删除
        @GetMapping("batchDelete")
        @ResponseBody
        public ResultBean deleteBatch(String ids) {
                String[] array = ids.split(",");
                boolean del = chatService.removeByIds(Arrays.asList(array));
                log.info("调用批量删除接口:{},数据:{}",del,ids);
                return del?ResultBean.ok():ResultBean.error400();
        }


        //查询所有数据
        @GetMapping("/queryAllData")
        public ResultBean queryAllData() {
            QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("create_time");
            List<Chat>  chats =  chatService.list(queryWrapper);
            return ResultBean.ok(chats);
        }

        //根据id查询
        @GetMapping("/queryOne")
        public ResultBean queryOne(Long id) {
            Chat  chat =   chatService.getById(id);
            return ResultBean.ok(chat);
        }


        //根据id查询
        @GetMapping("/queryCondition")
        public ResultBean queryCondition( Chat chat) {
             QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
             if( chat!=null){
                    if(chat.getId()!=null){
                        queryWrapper.eq("id",chat.getId());
                    }
                    if(chat.getContent()!=null){
                         queryWrapper.like("content",chat.getContent());
                    }
                    if(chat.getSendName()!=null){
                         queryWrapper.like("sendName",chat.getSendName());
                    }
                    if(chat.getRecvName()!=null){
                         queryWrapper.like("recvName",chat.getRecvName());
                    }
                    if(chat.getSendId()!=null){
                        queryWrapper.eq("sendId",chat.getSendId());
                    }
                    if(chat.getRecvId()!=null){
                        queryWrapper.eq("recvId",chat.getRecvId());
                    }
                 }
                 //queryWrapper.orderByDesc("create_time");
                 List<Chat>  chats = chatService.list(queryWrapper);
                 return ResultBean.ok(chats);
        }

        @GetMapping("/countCondition")
        public ResultBean countCondition( Chat chat) {
                QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
                if( chat!=null){
                        if(chat.getId()!=null){
                            queryWrapper.eq("id",chat.getId());
                        }
                        if(chat.getContent()!=null){
                            queryWrapper.like("content",chat.getContent());
                        }
                        if(chat.getSendName()!=null){
                            queryWrapper.like("sendName",chat.getSendName());
                        }
                        if(chat.getRecvName()!=null){
                            queryWrapper.like("recvName",chat.getRecvName());
                        }
                        if(chat.getSendId()!=null){
                            queryWrapper.eq("sendId",chat.getSendId());
                        }
                        if(chat.getRecvId()!=null){
                            queryWrapper.eq("recvId",chat.getRecvId());
                        }
                }
                int c = chatService.count(queryWrapper);
                return ResultBean.ok(c);
        }


        //分页查询
        @PostMapping("/page")
        public ResultBean page( PageTo<Chat> pageTo) {
            QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
            Chat  chat = pageTo.getObj();
            if( chat!=null){
                    if(chat.getId()!=null){
                         queryWrapper.eq("id",chat.getId());
                    }
                    if(chat.getContent()!=null){
                         queryWrapper.like("content",chat.getContent());
                    }
                    if(chat.getSendName()!=null){
                         queryWrapper.like("sendName",chat.getSendName());
                    }
                    if(chat.getRecvName()!=null){
                         queryWrapper.like("recvName",chat.getRecvName());
                    }
                    if(chat.getSendId()!=null){
                         queryWrapper.eq("sendId",chat.getSendId());
                    }
                    if(chat.getRecvId()!=null){
                         queryWrapper.eq("recvId",chat.getRecvId());
                    }
            }
            queryWrapper.orderByDesc("create_time");
            Page<Chat> p =chatService.page(new Page<>(pageTo.getPage(), pageTo.getSize()), queryWrapper);
            return ResultBean.ok(p);
        }


    @Autowired
    ChatMapper chatMapper;

    @RequestMapping("queryTxYh")
    @ResponseBody
    public ResultBean queryTxYh(Long id) {
        List<Chat> mChats = chatMapper.queryTxYh(id);
        return ResultBean.ok(mChats);
    }
    @RequestMapping("queryYxQb")
    @ResponseBody
    public ResultBean queryYxQb(Long myId,Long hisId) {
        List<Chat> mChats = chatMapper.queryYxQb(myId,hisId);
        return ResultBean.ok(mChats);
    }

}




