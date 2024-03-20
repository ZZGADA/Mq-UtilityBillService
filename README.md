
# 作者：ZZGEDA

# 项目所需的技术栈：
* SpringBoot  
* Mybatis-Plus  
* MySQL  
* RabbitMq
# 项目架构&层级设计
🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯🎯   
  
    ├─src                               # 基于httpclient 向学校容大后勤服务发送请求 并基于Mq异步发送邮件  
    │  ├─main  
    │  │  ├─java   
    │  │  │  └─com   
    │  │  │      └─example  
    │  │  │          └─consumer   
    │  │  │              ├─config       # 配置类 包括Mybatis-Plus配置、MqJackson2JsonMessageConverter序列化、Httpclient配置     
    │  │  │              ├─controller   # controller 接口服务调用     
    │  │  │              ├─exception    # exception 自定义异常和全局异常监听器    
    │  │  │              ├─listener     # listener Mq消息队列消费消息、包括exchange和queue的绑定  😻（关键）   
    │  │  │              ├─mapper       # mapper Mybatis-plus的数据库操作   
    │  │  │              ├─pojo         # pojo 自定义对象     
    │  │  │              │  ├─dto       # dto 数据传输层对象 层级之间信息传输对象   
    │  │  │              │  ├─entity    # entity 实体类型    
    │  │  │              │  └─po        # po 持久层对象   
    │  │  │              ├─service      # service 核心服务   
    │  │  │              └─utils        # 工具包 封装了httpclient的请求、Mail邮件发送  😻（关键）   
    │  │  └─resources   
    │  │      └─mapper                  # mapper sql的xml文件封装   
    │  └─test                           # test 测试文件   
🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀  

# 业务需求：
`邮件发送`：通过邮件，向用户发送宿舍的剩余电量  
`定时任务`：设定定时任务，间隔固定时间查询用户宿舍的剩余电量，当剩余电量低于阈值将自动发送邮件给用户  
👍👍👍👍👍👍👍👍👍👍👍👍👍👍👍👍👍  

# 目标实现：  
#####  1、向学校容大后勤的服务接口发起http请求，并解析json提取我们想要的字段数据 ✔  
#####  2、基于SMTP协议，向用户发送Mail邮件 ✔  
#####  3、由于发送邮件是一个耗时的业务，我们通过RabbitMq实现异步的Mail邮件发送 ✔  
#####  4、定时任务，向容大后勤服务接口发送http请求，判断用户宿舍电费是否低于阈值 ❌ 未实现  
