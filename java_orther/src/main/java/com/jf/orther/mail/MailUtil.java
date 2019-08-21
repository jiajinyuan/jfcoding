package com.jf.orther.mail;


import com.jf.orther.mail.model.MailContext;
import com.jf.orther.mail.model.MailServer;
import com.jf.orther.mail.model.MailUser;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 邮件工具类
 *
 * @author Junfeng
 */
public class MailUtil {

    /**
     * 5M
     */
    private static final Long MAX_SIZE = 1024 * 1024 * 5L;

    public static void sendMail(MailServer server, MailUser sender, MailUser recipient,
                                MailContext context) throws MessagingException, IOException {
        sendMail(server, sender, recipient, context, null);
    }

    public static void sendMail(MailServer server, MailUser sender, MailUser recipient,
                                MailContext context, File file) throws MessagingException, IOException {

        sender.validate();
        sender.validate();
        if (null == sender.getPwd() || "".equals(sender.getPwd())) {
            throw new IllegalArgumentException("Mail password cannot be empty");
        }
        recipient.validate();
        context.validate();

        Properties props = new Properties();
        props.setProperty("mail.host", server.getHost());
        props.setProperty("mail.smtp.auth", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender.getName(), sender.getPwd());
            }
        };
        Session session = Session.getInstance(props, authenticator);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender.getMail()));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient.getMail()));
        //设置标题
        message.setSubject(context.getSubject());
        if (null != file) {
            if (file.isDirectory() || !file.exists()) {
                throw new IllegalArgumentException("Argument #file invalid");
            }
            if (file.length() > MAX_SIZE) {
                throw new IllegalArgumentException("Argument #file is to big");
            }
            setMultipart(message, context, file);
        } else {
            //设置正文
            message.setContent(context.getMessage(), "text/html;charset=utf-8");
        }
        //发送
        Transport.send(message);
    }

    public static void sendGroupMail(MailServer server, MailUser sender, Set<MailUser> recipients,
                                     MailContext context) throws MessagingException, IOException {
        sendGroupMail(server, sender, recipients, context, null);
    }

    public static void sendGroupMail(MailServer server, MailUser sender, Set<MailUser> recipients,
                                     MailContext context, File file) throws MessagingException, IOException {
        if (null == recipients || recipients.size() == 0) {
            throw new IllegalArgumentException("Argument #recipients must not be null!");
        }
        for (MailUser recipient : recipients) {
            sendMail(server, sender, recipient, context, file);
        }
    }


    public static void setMultipart(MimeMessage msg, MailContext context, File file) throws MessagingException, IOException {
        // 一个Multipart对象包含一个或多个BodyPart对象，来组成邮件的正文部分（包括附件）。
        MimeMultipart multiPart = new MimeMultipart();

        // 添加正文
        MimeBodyPart partText = new MimeBodyPart();
        partText.setContent(context.getMessage(), "text/html;charset=utf-8");
        // 添加文件 也就 是附件
        MimeBodyPart partFile = new MimeBodyPart();
        partFile.attachFile(file);
        // 设置在收件箱中和附件名 并进行编码以防止中文乱码
        partFile.setFileName(MimeUtility.encodeText(file.getName()));
        multiPart.addBodyPart(partText);
        multiPart.addBodyPart(partFile);
        msg.setContent(multiPart);
    }

    public static void main(String[] arg) throws MessagingException, IOException {
        MailServer server = MailServer.builder().host("smtp.163.com").build();
        MailContext context = MailContext.builder().message("<p>&nbsp;&nbsp;&nbsp;&nbsp;<b>&lt;参数1&gt;</b>领导驾驶舱总体访问量为<b>&lt;参数2&gt;</b>（移动端<b>&lt;参数3&gt;</b>，PC端<b>&lt;参数4&gt;</b>）。其中航线效益总体访问量为<b>&lt;参数5&gt;</b>（移动端<b>&lt;参数6&gt;</b>，PC端<b>&lt;参数7&gt;</b>），行业对比总体访问量为<b>&lt;参数8&gt;</b>（移动端<b>&lt;参数9&gt;</b>，PC端<b>&lt;参数10&gt;</b>）。</p>\n" +
                "<p>&nbsp;&nbsp;&nbsp;&nbsp;访问次数最多的前两个模块为<b>&lt;参数11&gt;</b>（移动端<b>&lt;参数12&gt;</b>，pc端<b>&lt;参数13&gt;</b>），<b>&lt;参数14&gt;</b>（移动端<b>&lt;参数15&gt;</b>，pc端<b>&lt;参数16&gt;</b>）</p>\n" +
                "<p>&nbsp;&nbsp;&nbsp;&nbsp;访问次数最多的前三名用户为：<b>&lt;参数17&gt;</b>，<b>&lt;参数18&gt;</b>，<b>&lt;参数19&gt;</b>。</p>").subject("2018-03 效能统计通知").build();
//      MailContext context = MailContext.newBuilder().message("然后我看了下自己的备注发现并没有包含什么敏感信息，接着换一个账号这个账号之前注册过，不是当天的申请的授权码，").subject("这个问题等到第二天再试下，应该就可以了").build();
        MailUser sender = MailUser.builder().mail("jia_453@163.com").name("jia_453").pwd("jiajinyuan918").build();
        MailUser recipient = MailUser.builder().mail("jiajinyuan918@163.com").build();
        MailUser recipient2 = MailUser.builder().mail("wow2378083@163.com").build();

        Set<MailUser> recipients = new HashSet<>();
        recipients.add(recipient);
//        recipients.add(recipient2);

        File file = new File("D:\\CopyrightTest\\image\\迪丽热巴.jpg");

        sendGroupMail(server, sender, recipients, context);
    }
}
