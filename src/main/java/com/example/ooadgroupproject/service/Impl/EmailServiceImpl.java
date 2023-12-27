package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private static String generateVerificationCode() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = upperCaseLetters.toLowerCase();
        String numbers = "0123456789";
        String combinedChars = upperCaseLetters + lowerCaseLetters + numbers;

        Random random = new Random();
        char[] code = new char[6];
        for (int i = 0; i < 6; i++) {
            code[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(code);
    }
    private String userVerificationCode = null;
    @Override
    public Result sendVerifyCodeEmail(String userMail) {
        String userVerificationCode=generateVerificationCode();
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(userMail);
            mailMessage.setSubject("Sustech Campus 邮箱验证");
            //发送内容
            String content="尊敬的先生/女士，您好！您的邮箱验证码为： "+userVerificationCode;
            mailMessage.setText(content);
            javaMailSender.send(mailMessage);
            this.userVerificationCode = userVerificationCode;
        } catch (MailException e) {
            return Result.fail("发送给"+userMail+"的验证码未能发送成功");
        }
        return Result.success("发送给"+userMail+"的验证码"+userVerificationCode+"发送成功");
    }

    @Override
    public boolean verifyCode(String code) {
        return this.userVerificationCode.equals(code);
    }

    @Override
    public Result sendReservationCanceledEmail(String userMail, String roomName, Date date) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(userMail);
            mailMessage.setSubject("Sustech Campus 预约取消通知");
            String content="尊敬的先生/女士，您好！\n" +
                    "很抱歉的通知您，您于"+date+"日"+"在"+roomName+"的预约已被取消。\n" +
                    "详情请联系管理员。对于给您带来的不便，我们感到很抱歉";
            mailMessage.setText(content);
            javaMailSender.send(mailMessage);
            return Result.success("发送给"+userMail+"的预约取消通知成功");
        } catch (MailException e){
            return Result.fail("发送给"+userMail+"的预约取消通知未能发送成功");
        }
    }

}
