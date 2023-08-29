package com.base.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 发送名陈
     */
    @TableField(value = "sendName")
    private String sendName;

    /**
     * 接收名陈
     */
    @TableField(value = "recvName")
    private String recvName;

    /**
     * 发送id
     */
    @TableField(value = "sendId")
    private Long sendId;

    /**
     * 接收id
     */
    @TableField(value = "recvId")
    private Long recvId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;


}
