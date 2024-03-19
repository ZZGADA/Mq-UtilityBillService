package com.example.consumer.service;

import com.example.consumer.mapper.UtilityBillMapper;
import com.example.consumer.mapper.UtilityBillUserMapper;
import com.example.consumer.pojo.dto.MailSendingMqDTO;
import com.example.consumer.pojo.dto.UtilityBillDTO;
import com.example.consumer.pojo.dto.UtilityBillUserLocationDTO;
import com.example.consumer.pojo.entity.PostRequestEnum;
import com.example.consumer.utils.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UtilityBillsService {
    //    @Resource //依赖注入 与@Autowired一致 @Resource默认按byName自动注入
    private final MailSendingService mailSendingService;
    //    @Autowired
    private final HttpUtil httpUtil;
    private final UtilityBillUserMapper utilityBillUserMapper;
    private final UtilityBillMapper utilityBillMapper;
    private final RabbitTemplate rabbitTemplate;


    private static final String cookie = "shiroJID=341b57d5-d882-4c9b-acc7-443f81d0b6a4";
    private static final String utilityBillUrl = "https://application.xiaofubao.com/app/electric/queryISIMSRoomSurplus";

    //接收人
    private static final String recipient = "1293177585@qq.com";

    /**
     * 向数据库查询发送邮箱的人的宿舍电费信息，然后将查询到的电费信息通过ZZGEDA的QQMail 发送给userRecipient这个邮箱
     *
     * @param userRecipient: 发送邮箱的邮箱号码
     * @return [java.lang.String]
     */

    public String getUtilityBill(String userRecipient) {
        String bill;
        UtilityBillDTO utilityBillDTO = new UtilityBillDTO();
        // 如果不用https会发生重定型 然后需要手动重定向发送请求
        Map<String, String> headers = new HashMap<>();
        Map<String, Object> formBody = new HashMap<>();

        UtilityBillUserLocationDTO utilityBillUserExceptMail = utilityBillUserMapper.getUtilityBillUserExceptMail(userRecipient);

        utilityBillDTO.setAreaId(utilityBillUserExceptMail.getUniversityCodeId());
        utilityBillDTO.setBuildingCode(utilityBillUserExceptMail.getDormitoryId());
        utilityBillDTO.setFloorCode(utilityBillUserExceptMail.getDormitoryRoomId() / 100);
        utilityBillDTO.setRoomCode(utilityBillUserExceptMail.getDormitoryRoomId());
        utilityBillDTO.setYmId("");
        utilityBillDTO.setPlatform("");

        // 设置请求头
        headers.put("Content-Type", PostRequestEnum.ContentTypeXWWWForm.getValue());
        headers.put("Cookie", utilityBillMapper.getUtilityBillCookieByUniversityCode(userRecipient));

        // 设置请求体
        // getFiled只能获取public公共属性 对于私有属性的获取只能通过getDeclaredFields
        for (Field item : utilityBillDTO.getClass().getDeclaredFields()) {
            try {
                // 属性配置允许访问私有属性
                item.setAccessible(true);
                formBody.put(item.getName(), item.get(utilityBillDTO));
                item.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            JsonNode jsonNode = new ObjectMapper().readTree(httpUtil.doPost(utilityBillUrl, formBody, headers)).get("data").get("soc");
            bill = jsonNode.asText();
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
        return bill;
    }

    public void sendBill(String recipient) {
        String dormitoryBill = getUtilityBill(recipient);
        if (!Objects.isNull(dormitoryBill)) {
//            mailSendingService.sendMailFormQQMail(recipient,  );
//            MessagePostProcessor messagePostProcessor = message -> {
//                message.getMessageProperties().setMessageId(recipient);
//                return message;
//            };
            // lambda 传入一个自定义方法 这种使用就可以算作是回调函数了
            rabbitTemplate.convertAndSend("mail.direct", "mail", new MailSendingMqDTO(recipient, dormitoryBill), (message -> {
                message.getMessageProperties().setMessageId(recipient);
                return message;
            }));
        }
        System.out.println("====>  mq异步消息发送成功");
    }

}
