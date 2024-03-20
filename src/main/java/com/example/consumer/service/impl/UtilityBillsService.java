package com.example.consumer.service.impl;

import com.example.consumer.mapper.UtilityBillMapper;
import com.example.consumer.mapper.UtilityBillUserMapper;
import com.example.consumer.pojo.dto.MailSendingMqDTO;
import com.example.consumer.pojo.dto.UtilityBillDTO;
import com.example.consumer.pojo.dto.UtilityBillUserLocationDTO;
import com.example.consumer.pojo.entity.PostRequestEnum;
import com.example.consumer.service.IUtilityBillsService;
import com.example.consumer.utils.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UtilityBillsService implements IUtilityBillsService {
    //    @Resource //依赖注入 与@Autowired一致 @Resource默认按byName自动注入
    private final HttpUtil httpUtil;
    private final UtilityBillUserMapper utilityBillUserMapper;
    private final UtilityBillMapper utilityBillMapper;
    private final RabbitTemplate rabbitTemplate;
    private static final String utilityBillUrl = "https://application.xiaofubao.com/app/electric/queryISIMSRoomSurplus";
    private final JedisPool jedisPool;
    private final Long utilityBillThreshold=50L;


    /**
     * 向数据库查询发送邮箱的人的宿舍电费信息，然后将查询到的电费信息通过ZZGEDA的QQMail 发送给userRecipient这个邮箱
     *
     * @param userRecipient: 发送邮箱的邮箱号码
     * @return [java.lang.String]
     */
    @Override
    public String getUtilityBill(String userRecipient) {
        String bill;
        UtilityBillDTO utilityBillDTO;
        // 如果不用https会发生重定型 然后需要手动重定向发送请求


        UtilityBillUserLocationDTO utilityBillUserExceptMail = utilityBillUserMapper.getUtilityBillUserExceptMail(userRecipient);
        // 为空则返回
        if (Objects.isNull(utilityBillUserExceptMail)) {
            return "";
        } else {
            utilityBillDTO = new UtilityBillDTO();
        }

        utilityBillDTO.setAreaId(utilityBillUserExceptMail.getUniversityCodeId());
        utilityBillDTO.setBuildingCode(utilityBillUserExceptMail.getDormitoryId());
        utilityBillDTO.setFloorCode(utilityBillUserExceptMail.getDormitoryRoomId() / 100);
        utilityBillDTO.setRoomCode(utilityBillUserExceptMail.getDormitoryRoomId());
        utilityBillDTO.setYmId("");
        utilityBillDTO.setPlatform("");


        try {
            // 基于反射将utilityBillDTO处理成Map对象
            JsonNode jsonNode = new ObjectMapper().
                    readTree(httpUtil.doPost(utilityBillUrl, getFormBody(utilityBillDTO), getHeaders(userRecipient))).
                    get("data").get("soc");
            bill = jsonNode.asText();
        } catch (Exception e) {
            log.error(e.toString());
            return "";
        }
        return bill;
    }


    @Override
    public String getUtilityBill(UtilityBillDTO utilityBillDTO) {
        return null;
    }

    /**
     * getUtilityBill获得用户宿舍的剩余水电之后 将查询到的水电费bill传输给mq
     * 通过mq实现异步的邮件发送
     *
     * @param recipient: 接收方的邮箱地址
     */

    @Override
    public void sendBill(String recipient) {
        String dormitoryBill = getUtilityBill(recipient);
        if (!dormitoryBill.isEmpty()) {
            // lambda 传入一个自定义方法 这种使用就可以算作是回调函数了
            rabbitTemplate.convertAndSend("mail.direct", "mail", new MailSendingMqDTO(recipient, dormitoryBill), (message -> {
                message.getMessageProperties().setMessageId(recipient);
                return message;
            }));
        }
        System.out.println("====>  mq异步消息发送成功");
    }

    // 定时任务 每天下午14点执行
    @Override
    @Scheduled(cron = "0 0 14 * * ?")
    public void queryAllUserSendMessage() {
        // 查询所有用户 和 查询所有用户的unique宿舍号 减少请求发送
        String bill;

        List<UtilityBillUserLocationDTO> allUtilityBillUser = utilityBillUserMapper.getAllUtilityBillUser();
        List<UtilityBillUserLocationDTO> dormitoryRoomIdsGroupBy = utilityBillUserMapper.getDormitoryRoomIdsGroupBy();
        Map<String, String> headerSchedule = getHeaderSchedule();
        StringBuilder dormitoryLocationKey = new StringBuilder();
        UtilityBillDTO utilityBillDTO = new UtilityBillDTO();
        utilityBillDTO.setYmId("");
        utilityBillDTO.setPlatform("");

        // 从线程池中申请资源
        Jedis redisResource = jedisPool.getResource();
        try{
            // 循环获得宿舍号的剩余电费 并将学校号_宿舍号为key 将电费bill为value 存入redis
            for (UtilityBillUserLocationDTO dormitoryItem : dormitoryRoomIdsGroupBy) {
                log.info(dormitoryItem.toString());
                // 转换成DTO对象
                utilityBillDTO.setAreaId(dormitoryItem.getUniversityCodeId());
                utilityBillDTO.setBuildingCode(dormitoryItem.getDormitoryId());
                utilityBillDTO.setFloorCode(dormitoryItem.getDormitoryRoomId() / 100);
                utilityBillDTO.setRoomCode(dormitoryItem.getDormitoryRoomId());
                // redis设置key
                dormitoryLocationKey.append(dormitoryItem.getUniversityCodeId()).
                        append("_").
                        append(dormitoryItem.getDormitoryRoomId());
                // 修改header
                headerSchedule.put(PostRequestEnum.Cookie.getValue(), dormitoryItem.getCookie());

                try {
                    // 基于反射将utilityBillDTO处理成Map对象 并发送http请求
                    JsonNode jsonNode = new ObjectMapper().
                            readTree(httpUtil.doPost(utilityBillUrl, getFormBody(utilityBillDTO), headerSchedule)).
                            get("data").get("soc");
                    bill = jsonNode.asText();
                    log.info(String.format("%s_%s 剩余电量:%s",dormitoryItem.getUniversityCodeId(),dormitoryItem.getDormitoryRoomId(),bill));
                    // redis插入数据  TODO:后面改成倒计时的 或者最后删除掉 但也可以不用做处理 只有用户注销的时候 才删除指定的Key
                    redisResource.set(dormitoryLocationKey.toString(),bill);
                } catch (Exception e) {
                    log.error(e.toString());
                } finally {
                    dormitoryLocationKey.setLength(0);
                }
            }

            log.info("https请求发送成功 以下是发送qqMail");
            // 遍历学生信息 向redis中查询上面的key (学校号_宿舍号) 获得bill数据 异步发送qq邮箱
            for (UtilityBillUserLocationDTO userItem : allUtilityBillUser){
                log.info("=====> 待发送邮件信息");
                log.info(userItem.toString());
                dormitoryLocationKey.append(userItem.getUniversityCodeId()).
                        append("_").
                        append(userItem.getDormitoryRoomId());
                // redis读取数据
                bill = redisResource.get(dormitoryLocationKey.toString());
                if (!bill.isEmpty()) {
                    //如果小于水电费阈值：50就发送短信，否则不发
                    if(Long.parseLong(bill)<this.utilityBillThreshold) {
                        // lambda 传入一个自定义方法 这种使用就可以算作是回调函数了
                        rabbitTemplate.convertAndSend("mail.direct", "mail", new MailSendingMqDTO(userItem.getMail(), bill), (message -> {
                            message.getMessageProperties().setMessageId(userItem.getMail());
                            return message;
                        }));
                    }
                }
                // 重置 StringBuilder
                dormitoryLocationKey.setLength(0);
            }

        }catch (Exception e){
            log.error(e.toString(),e);
        }finally {
            // 关闭线程资源
            redisResource.close();
        }

    }

    @Scheduled(cron = "*/5 * * * * *")
    public  void scheduleTask(){
        System.out.println("test test test test test test test test ");
        System.out.println(Thread.currentThread().getName());
    }


    public Map<String, Object> getFormBody(UtilityBillDTO utilityBillDTO) throws RuntimeException {
        Map<String, Object> formBody = new HashMap<>();

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

        return formBody;
    }

    public Map<String, String> getHeaders(String userRecipient) throws RuntimeException {
        Map<String, String> headers = new HashMap<>();
        // 设置请求头
        headers.put(PostRequestEnum.Content_Type.getValue(), PostRequestEnum.ContentTypeXWWWForm.getValue());
        headers.put(PostRequestEnum.Cookie.getValue(), utilityBillMapper.getUtilityBillCookieByUniversityCode(userRecipient));
        return headers;
    }

    public Map<String, String> getHeaderSchedule() throws RuntimeException {
        Map<String, String> headers = new HashMap<>();
        // 设置请求头
        headers.put(PostRequestEnum.Content_Type.getValue(), PostRequestEnum.ContentTypeXWWWForm.getValue());
        headers.put(PostRequestEnum.Cookie.getValue(), "");
        return headers;
    }


}
