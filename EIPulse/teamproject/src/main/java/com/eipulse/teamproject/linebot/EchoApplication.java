package com.eipulse.teamproject.linebot;


import com.eipulse.teamproject.dto.clocktimedto.ClockTimeDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.service.clocktimeservice.ClockTimeService;
import com.eipulse.teamproject.service.employeeservice.EmployeeService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import io.netty.handler.codec.http.HttpRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

//Line打卡功能，9/16實現能帶入event事件待更新...
@LineMessageHandler
public class EchoApplication {

    private EmployeeService employeeService;
    private ClockTimeService clockTimeService;

    @Autowired
    public EchoApplication(EmployeeService employeeService, ClockTimeService clockTimeService) {
        this.employeeService = employeeService;
        this.clockTimeService = clockTimeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        String responseMessage="";
        String userMessage = event.getMessage().getText();

        Employee  employee=employeeService.getEmpLineId(event.getSource().getUserId());
        if(employee ==null){
            return new TextMessage("尚未註冊LineID，請依序輸入員工Id，信箱");
        }else if(employee!=null){
//            clockTimeService.saveClockTime(new ClockTimeDTO(employee.getEmpId(),))
        }

        System.out.println("收到的訊息:"+userMessage);
        String userId = event.getSource().getUserId();
        System.out.println("員工LineId: " +userId);
//        if(userId)



        return new TextMessage(responseMessage);
    }

//    LocalDateTime clockTime = LocalDateTime.now();
//    int year = clockTime.getYear();
//    int month = clockTime.getMonthValue();
//    int day = clockTime.getDayOfMonth();
//    int hour = clockTime.getHour();
//    int minute = clockTime.getMinute();
//    String time = year + "-" + month + "-" + day + "-" + hour + ":" + minute;
//        if (userMessage.equals("上班")) {
//        responseMessage = "上班打卡成功\n" + time;
//    } else if (userMessage.equals("下班")) {
//        responseMessage = "下班打卡成功\n" + time;
//    } else {
//        responseMessage = "共三小";
//    }

}