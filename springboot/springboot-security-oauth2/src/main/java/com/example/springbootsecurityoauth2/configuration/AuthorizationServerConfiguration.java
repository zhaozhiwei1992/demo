package com.example.springbootsecurityoauth2.configuration;

import com.example.springbootsecurityoauth2.domain.OAuth2Client;
import com.example.springbootsecurityoauth2.provider.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

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
        //配置多个Client
        List<OAuth2Client> clientList = Arrays.asList(
                new OAuth2Client("clientId", "clientSecret", 7200),
                new OAuth2Client("clientId2", "clientSecret2", 0)
        );

        final InMemoryClientDetailsServiceBuilder inMemoryClientDetailsServiceBuilder = clients.inMemory();

        clientList.forEach(clienObj ->{
            inMemoryClientDetailsServiceBuilder // 使用in-memory存储
                    .withClient(clienObj.getClientId())
                    .secret(new BCryptPasswordEncoder().encode(clienObj.getClientSecret()))
                    // token的生效时间2小时, 0 表示永久生效不过期
                    .accessTokenValiditySeconds(clienObj.getAccessTokenValiditySeconds())
                    .refreshTokenValiditySeconds(259200)
//                    .authorizedGrantTypes("authorization_code")
                    .authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit")
                    .scopes("all", "read", "write")
//                    .scopes("all")
                    .redirectUris("http://www.baidu.com");
        });
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        // jwt增强器
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(jwtTokenEnhancer());
        enhancers.add(jwtAccessTokenConverter());
        enhancerChain.setTokenEnhancers(enhancers);

        endpoints
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter());
    }

//    @Bean
//    public TokenStore memeryTokenStore() {
//        return new InMemoryTokenStore();
//    }

//    @Bean
//    public TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory());
//    }
//
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }

    /**
     * @data: 2021/6/9-下午7:50
     * @User: zhaozhiwei
     * @method: tokenStore

     * @return: org.springframework.security.oauth2.provider.token.TokenStore
     * @Description: oauth2中生成的token是一个uuid值，其值本身是没有任何意义的，我们可以将替换成json web token, 简称 JWT
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("zzw_JwtKey");
        return accessTokenConverter;
    }

    /**
     * token增强器
     * @return
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

}