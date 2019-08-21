package com.jf.orther.mail.model;

import lombok.Builder;
import lombok.Data;

/**
 * 发件人信息
 *
 * @author Junfeng
 */
@Builder
@Data
public class MailUser {
    /**
     * 发件人昵称
     */
    private String name;
    /**
     * 发件人邮箱
     */
    private String mail;
    /**
     * 发件人密码
     */
    private String pwd;

    public void validate() {
        if (null == mail || "".equals(mail)) {
            throw new IllegalArgumentException("Mail address cannot be empty");
        }
    }
}
