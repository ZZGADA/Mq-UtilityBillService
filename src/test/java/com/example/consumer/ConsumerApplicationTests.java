package com.example.consumer;

import com.example.consumer.pojo.Mail;
import com.example.consumer.pojo.dto.UtilityBillDTO;
import com.example.consumer.pojo.entity.MailEnum;
import com.example.consumer.pojo.entity.UtilityBillEnum;
import com.example.consumer.utils.HttpUtil;
import com.example.consumer.utils.MailContextUtil;
import com.example.consumer.utils.MailUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
//@RequiredArgsConstructor
class ConsumerApplicationTests {

    @Resource //依赖注入 与@Autowired一致 @Resource默认按byName自动注入
    private MailUtil mailUtil;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private MailContextUtil mailContextUtil;
    private static final String cookie = "shiroJID=341b57d5-d882-4c9b-acc7-443f81d0b6a4";

    //接收人
//    private static final String recipient = "leon.bwchen@foxmail.com";

//    @Test
//    void MailSending(String bills) {
//        Mail mail = new Mail();
////        int code = (int) ((Math.random() * 9 + 1) * 100000);
//        mail.setRecipient(recipient);
//        mail.setSubject("张赞发来的测试邮件");
//        mail.setContent("亲爱的用户：您好！\n" +
//                "\n" +
//                "\t这是一封来自ZZGEDA_张赞的邮件，请不用理会这封电子邮件。\n" +
//                "\t但是：您房间电费剩余 " + bills + "度，如果电力不足请及时充电\n" +
//                "\t咨询人：ZZGEDA_张赞 联系电话：19902900670\n"
//        );
//        mailUtil.sendSimpleMail(mail);
//    }
//
////    @Test
//    void postWeChatApiOfUtilityBills() {
//        UtilityBillDTO utilityBillDTO = new UtilityBillDTO();
//        // 如果不用https会发生重定型 然后需要手动重定向发送请求
//        String url = "http://application.xiaofubao.com/app/electric/queryISIMSRoomSurplus";
//        Map<String, String> headers = new HashMap<>();
//        Map<String, Object> formBody = new HashMap<>();
//
//        utilityBillDTO.setAreaId("1908211437059099");
//        utilityBillDTO.setBuildingCode(31);
//        utilityBillDTO.setFloorCode(3101);
//        utilityBillDTO.setRoomCode(310123);
//        utilityBillDTO.setYmId("");
//        utilityBillDTO.setPlatform("");
//        // getFiled只能获取public公共属性 对于私有属性的获取只能通过getDeclaredFields
//        for (Field item : utilityBillDTO.getClass().getDeclaredFields()) {
//            try {
//                // 属性配置允许访问私有属性
//                item.setAccessible(true);
//                formBody.put(item.getName(), item.get(utilityBillDTO));
//                item.setAccessible(false);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//
//        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        headers.put("Cookie", cookie);
//        try {
//            JsonNode jsonNode = new ObjectMapper().readTree(httpUtil.doPost(url, formBody, headers)).get("data").get("soc");
//            String bill = jsonNode.asText();
//            System.out.println(bill);
//            MailSending(bill);
//        } catch (Exception e) {
//            log.error(e.toString());
//        }
//    }

//    @Test
//    public void MailEnumTest() {
//        System.out.println(MailEnum.MailSendingUserName.getClass());
//        System.out.println(MailEnum.MailSendingUserName.getContent());
//        System.out.println(MailEnum.MailSubjectTest);
//
//        String res = mailContextUtil.getFullMailContextMessage(UtilityBillEnum.class, "89.7");
//        System.out.println(res);
//    }
}
