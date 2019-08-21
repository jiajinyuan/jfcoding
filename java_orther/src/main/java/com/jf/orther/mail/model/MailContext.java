package com.jf.orther.mail.model;

import lombok.Builder;
import lombok.Data;

/**
 * 邮件内容
 *
 * @author Junfeng
 */
@Data
@Builder
public class MailContext {
    /**
     * 主题
     */
    private String subject;
    /**
     * 信息(支持HTML)
     */
    private String message;

    public void validate(){
        if(null == subject || "".equals(subject)){
            throw new IllegalArgumentException("Mail subject cannot be empty");
        }
    }
}
