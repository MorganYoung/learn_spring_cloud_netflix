### spring cloud netflix 微服务



####主要组件

* eureka做服务发现
* config做分布式配置 sping cloud config
* zuul做api-gateway
* feign做客户端负载均衡
* hystrix做断路器
* turbine做聚合的monitor
* graphite做指标监控

####组件介绍
####Eureka
Eureka由两个组件组成：Eureka服务器和Eureka客户端。Eureka服务器用作服务注册服务器。Eureka客户端是一个java客户端，用来简化与服务器的交互、作为轮询负载均衡器，并提供服务的故障切换支持。Netflix在其生产环境中使用的是另外的客户端，它提供基于流量、资源利用率以及出错状态的加权负载均衡。

当一个中间层服务首次启动时，他会将自己注册到Eureka中，以便让客户端找到它，同时每30秒发送一次心跳。如果一个服务在几分钟内没有发送心跳，它将从所有Eureka节点上注销。Eureka使用Servo进行性能监控和告警。

```
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}

```

#### spring cloud config
Spring Cloud Config会运行一个名为Config Server的小巧服务，并通过REST API提供可集中访问的配置数据。默认情况下配置数据存储在一个Git仓库中，并可通过标准的PropertySource抽象暴露给Spring Boot服务。使用PropertySource可将本地属性文件中包含的配置与Config Server中存储的配置无缝结合在一起。对于本地开发，可以使用来自本地属性文件的配置，并只在将应用程序部署在现实环境时才覆盖这些配置信息。

Spring Cloud Config会运行一个名为Config Server的小巧服务，并通过REST API提供可集中访问的配置数据。默认情况下配置数据存储在一个Git仓库中，并可通过标准的PropertySource抽象暴露给Spring Boot服务。使用PropertySource可将本地属性文件中包含的配置与Config Server中存储的配置无缝结合在一起。对于本地开发，可以使用来自本地属性文件的配置，并只在将应用程序部署在现实环境时才覆盖这些配置信息。

```
@SpringBootApplication
@EnableConfigServer
public class RepmaxConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepmaxConfigServerApplication.class, args);
    }
}
```

其中@EnableConfigServer标注可用于通过小巧的Spring Boot应用程序启动Config Server。随后可以用spring.cloud.config.server.git.uri属性将Config Server指向Git代码库。对于本地测试工作，可将其加入Config Server应用程序的application.properties文件：

```
spring.cloud.config.server.git.uri=file://${user.home}/dev/repmax-config-repo
```

通过这种方式，团队中的每位开发者都可以在自己的计算机上启动Config Server，并通过本地Git代码库进行测试。

Spring Cloud Config Client

启用动态刷新

通过将配置信息集中存储在一个位置，可以轻松更改repmax配置，使其能够被所有服务直接使用。然而为了应用这些配置依然需要重启动服务。实际上可以通过更好的方式实现。可以借助Spring Boot提供的@ConfigurationProperties标注将配置直接映射给JavaBeans。Spring Cloud Config更进一步为每个客户端服务暴露了一个/refresh端点。带有@ConfigurationProperties标注的Bean可在通过/refresh端点触发刷新后更新自己的属性。

任何Bean均可添加@ConfigurationProperties标注，但是有必要对刷新操作进行限制，只应用于包含配置数据的Bean。为此可以用一个专门用于保存leaderboard地址的

```
@ConfigurationProperties("leaderboard.lb")
public class LeaderboardConfig {

    private volatile String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
```

@ConfigurationProperties标注的值实际上是希望映射至Bean的配置值的前缀。随后每个值可使用标准的JavaBean命名规则进行映射。这种情况下，url Bean属性可映射至配置中的leaderboard.lb.url。

为了触发配置刷新操作，可向logbook服务的/refresh端点发送一个HTTP POST请求：

```
curl -X POST http://localhost:8081/refresh
```

####Ribbon
Ribbon，简单说，主要提供客户侧的软件负载均衡算法。
Ribbon内置可插拔、可定制的负载均衡组件。下面是用到的一些负载均衡策略：
Ribbon 提供了框架来支持通用的 LB 机制，同时也提供了对 Netflix eureka 框架的支持。

* 简单轮询负载均衡
* 加权响应时间负载均衡
* 区域感知轮询负载均衡
* 随机负载均衡

#####区域感知负载均衡器
区域感知负载均衡器内置电路跳闸逻辑，可被配置基于区域同源关系（Zone Affinity，也就是更倾向于选择发出调用的服务所在的托管区域内，这样可用降低延迟，节省成本）选择目标服务实例。它监控每个区域中运行的实例的运维行为，而且能够实时快速丢弃一整个区域。

在选择服务器时，该负载均衡器会采取如下步骤：

1.负载均衡器会检查、计算所有可用区域的状态。如果某个区域中平均每个服务器的活跃请求已经达到配置的阈值，该区域将从活跃服务器列表中排除。如果多于一个区域已经到达阈值，平均每服务器拥有最多活跃请求的区域将被排除。

2.最差的区域被排除后，从剩下的区域中，将按照服务器实例数的概率抽样法选择一个区域。

3.从选定区域中，将会根据给定负载均衡策略规则返回一个服务器。

Why Eureka need Ribbon？

Eureka 附带客户端库，为何还要 Ribbon 呢？
Ribbon 的负载均衡算法、区域感知负载均衡器久经考验，可以直接拿来使用。

Why Ribbon need Eureka？

熟悉 Ribbon 的同学都知道，Ribbon 维护了一个服务器列表，如果服务器有宕机现象，Ribbon 能够自行将其剔除；但如果该服务器故障排除，重新启动，或者增加新的负载节点，我们需要手工调用 Ribbon 的接口将其动态添加进 Ribbon 的服务器列表。这样明显不够尽如人意。如何能够在服务节点启动时，自行添加服务列表？—— Eureka。Eureka 提供了 Application Service 客户端的自行注册的功能。此外，Eureka 的缓存机制能够防止大规模宕机带来的灾难性后果。

####参考
* [SpringCloud微服务实战](https://segmentfault.com/a/1190000005142460)
* [微服务框架Spring Cloud介绍 Part5: 在微服务系统中使用Hystrix, Hystrix Dashboard与Turbine](http://skaka.me/blog/2016/09/04/springcloud5/)
* [微服务框架Spring Cloud介绍 Part4: 使用Eureka, Ribbon, Feign实现REST服务客户端](http://skaka.me/blog/2016/08/25/springcloud4/)
* [Netflix发布云中间层服务开源项目Ribbon](http://www.infoq.com/cn/news/2013/01/netflix-annouced-ribbon)
* [Netflix开源他们的另一个架构——Eureka](http://www.infoq.com/cn/news/2012/09/Eureka)
* [使用Spring Cloud连接不同服务](http://www.infoq.com/cn/articles/spring-cloud-service-wiring)
* [Ribbon 和 Eureka 的集成](http://blog.csdn.net/defonds/article/details/38016301)
* [ 云中间层服务 - 区域感知负载均衡器 Ribbon](http://blog.csdn.net/defonds/article/details/32729155)
* [ Eureka 的 Application Client 客户端的运行示例](http://blog.csdn.net/defonds/article/details/37652111)