package com.xcar360.kafka.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息模板
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@Document(collection = "message_info")
public class MessageTemplate  implements Serializable {

    @Id
    @JsonIgnore
    protected ObjectId id;
    private String messageId; // 消息id（自动生成）
    private String messageInfo; // 消息信息
    private String messageQueueName; //队列名称

    private String mmessgeType;//队列类型

    private Integer mqStatus;
    private Date sendTime;
    private Date acceptTime; // 消息接收时间;

    private String topic;//消息主题

    private String phone;//电话


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public String getMessageQueueName() {
        return messageQueueName;
    }

    public void setMessageQueueName(String messageQueueName) {
        this.messageQueueName = messageQueueName;
    }

    public String getMmessgeType() {
        return mmessgeType;
    }

    public void setMmessgeType(String mmessgeType) {
        this.mmessgeType = mmessgeType;
    }

    public Integer getMqStatus() {
        return mqStatus;
    }

    public void setMqStatus(Integer mqStatus) {
        this.mqStatus = mqStatus;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
