package com.example.springbootsecurityoauth2.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    /**
     * @data: 2021/6/7-下午11:32
     * @User: zhaozhiwei
     * @method: configure
      * @param clients :
     * @return: void
     * @Description: 注意：对于clients.secret设置，网上大部分都是直接赋值一个明文，究竟设置为明文还是密文取决与SecurityConfiguration类中配置的PasswordEncoder
     * 是什么，如果PasswordEncoder为BCryptPasswordEncoder，此时clients
     * .secret也必须设置为BCryptPasswordEncoder加密后的密文，如果PasswordEncoder为NoOpPasswordEncoder，此时clients
     * .secret可以设置为明文。
     *
     * 需要使用restclient插件, 使用basicauthorization自动生成authorization信息
     * curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -H 'Authorization: Basic Y2xpZW50SWQ6Y2xpZW50U2VjcmV0' -i http://localhost:8080/authentication/form --data 'username=admin&password=11'
     *返回:
     * {"access_token":"95446e5f-1b5f-46a6-bff6-2404b0648dfd","token_type":"bearer","expires_in":43199,"scope":"all"}
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in-memory存储
                .withClient("clientId")
                .secret(new BCryptPasswordEncoder().encode("clientSecret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("all")
                .redirectUris("http://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }
}