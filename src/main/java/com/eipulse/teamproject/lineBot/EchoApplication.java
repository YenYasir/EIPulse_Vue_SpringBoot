//package com.eipulse.teamproject.lineBot;
//
//
//import com.eipulse.teamproject.service.ClockTimeServiceImp;
//import com.linecorp.bot.model.event.Event;
//import com.linecorp.bot.model.event.MessageEvent;
//import com.linecorp.bot.model.event.message.TextMessageContent;
//import com.linecorp.bot.model.message.TextMessage;
//import com.linecorp.bot.spring.boot.annotation.EventMapping;
//import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//
//import java.time.LocalDateTime;
//
////Line打卡功能，9/16實現能帶入event事件待更新...
//@LineMessageHandler
//public class EchoApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(EchoApplication.class, args);
//    }
//
//    @Autowired
//    private ClockTimeServiceImp clockTimeServiceImp;
//    @EventMapping
//    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
//        String userMessage = event.getMessage().getText();
//        String userId = event.getSource().getUserId();
////        if(userId)
//        String responseMessage;
//        LocalDateTime clockTime = LocalDateTime.now();
//        int year = clockTime.getYear();
//        int month = clockTime.getMonthValue();
//        int day = clockTime.getDayOfMonth();
//        int hour = clockTime.getHour();
//        int minute = clockTime.getMinute();
//        String time = year + "-" + month + "-" + day + "-" + hour + ":" + minute;
//        if (userMessage.equals("上班")) {
//            responseMessage = "上班打卡成功\n" + time;
//        } else if (userMessage.equals("下班")) {
//            responseMessage = "下班打卡成功\n" + time;
//        } else {
//            responseMessage = "共三小";
//        }
//
//
//        System.out.println("event: " + responseMessage);
//        return new TextMessage(responseMessage);
//    }
//
//    @EventMapping
//    public void handleDefaultMessageEvent(Event event) {
//        System.out.println("event: " + event);
//    }
//
//
//}