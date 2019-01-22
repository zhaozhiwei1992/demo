### 分别实现了jdk和spring下 jmx的使用
1. jdk中自定义mbean需要写接口，并且命名结尾必须是MBean
2. 动态的mbean可以直接使用jdk默认的一个标准实现
3. spring中可以结合springboot, + jolokia通过http进行监控