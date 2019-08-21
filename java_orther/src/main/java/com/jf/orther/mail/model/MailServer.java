package com.jf.orther.mail.model;

import lombok.Builder;
import lombok.Data;

/**
 * 邮件服务器信息
 *
 * @author Junfeng
 */
@Builder
@Data
public class MailServer {
    /**
     * 服务器地址
     */
    private String host;
    /**
     * 服务器端口号
     */
    private String portNumber;
}
