# zdd-shop-parent
项目简介


zdd-shop-parent-----公共Pranet接口
-----zdd-shop-basics----分布式基础设施
---------zdd-shop-basics-springcloud-eureka—注册中心 8080
---------zdd-shop-basics-apollo-config-server—阿波罗分布式配置中心
---------zdd-shop-basics-springcloud-zuul—统一请求入口 80
---------zdd-shop-basics-xuxueli-xxljob—分布式任务调度平台
---------zdd-shop-basics-codingapi-lcn—分布式事务解决框架
---------zdd-shop-basics-codingapi- ZipKin  —分布式调用链系统


-----zdd-shop-service-api提供公共接口
------------ zdd-shop-service-api-weixin 微信服务接口
------------ zdd-shop-service-api-member会员服务接口
------------ zdd-shop-service-api-sso  sso服务接口
------------ zdd-shop-service-api-item商品服务接口
------------ zdd-shop-service-api-search 搜索服务接口
------------ zdd-shop-service-api-pay聚合支付平台
------------ zdd-shop-service-api-order订单服务接口
------------ zdd-shop-service-api-spike 秒杀服务接口
服务接口中包含内存内容: 实体类层、接口层 

-----zdd-shop-service-impl公共接口的实现
------------ zdd-shop-service-weixin-impl 微信服务接口实现
------------ zdd-shop-service-member-impl会员服务接口实现
------------ zdd-shop-service-api-sso-imp  sso服务接口实现
------------ zdd-shop-service-tem-imp商品服务接口实现
------------ zdd-shop-service-search-imp 搜索服务接口实现
------------ zdd-shop-service-pay-imp聚合支付平台接口实现
------------ zdd-shop-service-orde-impr订单服务接口实现
------------ zdd-shop-service-api-spike-imp 秒杀服务接口

-----zdd-shop-common 工具类
---------zdd-shop-common-core—核心工具类

-----zdd-shop-portal 门户平台
--------zdd-shop-portal-web 门户网站 
--------zdd-shop-portal-sso 单点登陆系统 
--------zdd-shop-portal-search 搜索系统
--------zdd-shop-portal-spike 秒杀系统
--------zdd-shop-portal-cms 系统 
