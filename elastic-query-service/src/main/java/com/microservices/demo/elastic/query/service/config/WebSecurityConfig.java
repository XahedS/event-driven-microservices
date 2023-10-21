package com.microservices.demo.elastic.query.service.config;

import com.microservices.demo.elastic.query.service.security.TwitterQueryUserDetailsService;
import com.microservices.demo.elastic.query.service.security.TwitterQueryUserJwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //enable pre & post Authorization annotation to be used in method level.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //        Commented below while implementing Resource Server KeyCloak
//    private final UserConfigData userConfigData;
    private final TwitterQueryUserDetailsService twitterQueryUserDetailsService;
    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Value("${security.paths-to-ignore}")
    private String [] pathsToIgnore;

    public WebSecurityConfig(TwitterQueryUserDetailsService twitterQueryUserDetailsService,
                             OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.twitterQueryUserDetailsService = twitterQueryUserDetailsService;
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

//        Commented below while implementing Resource Server KeyCloak
//    public WebSecurityConfig(UserConfigData userConfigData) {
//        this.userConfigData = userConfigData;
//    }

    @Override
    public void configure(HttpSecurity  http) throws  Exception{
//        http.authorizeRequests().antMatchers("/**").permitAll();
        http
                .sessionManagement()//Call http session management and
//                Don't create JSESSIONID & avoid using cookies & re-authenticate client in each request
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf() //FOR Stateless session no need of csrf so disable it
                .disable()
                //Authorize requests and for any request enable full Authentication
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                // enable Oauth2ResourceServer
                .oauth2ResourceServer()
                //For JWT use custom jwt converter as defined in twitterQueryUserJwtConverter method
                .jwt()
                .jwtAuthenticationConverter(twitterQueryUserJwtConverter());
//        Commented below while implementing Resource Server KeyCloak
//        http.httpBasic().and().authorizeRequests().antMatchers("/**").hasRole("USER").
//                and().csrf().disable();
    }

    @Bean
    JwtDecoder jwtDecoder(@Qualifier("elastic-query-service-audience-validator")OAuth2TokenValidator<Jwt> audienceValidator){
//        Nimbus is the underlying library that spring security uses for handling Jwt operations
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(
                oAuth2ResourceServerProperties.getJwt().getIssuerUri()
        );

        OAuth2TokenValidator<Jwt> withIssuer = //Issuer validator automatically adds timestamp expiration validator
                JwtValidators.createDefaultWithIssuer(
                        oAuth2ResourceServerProperties.getJwt().getIssuerUri());

        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator); //Audience Validator
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }
    @Bean
    public Converter<Jwt,? extends AbstractAuthenticationToken> twitterQueryUserJwtConverter() {
        return new TwitterQueryUserJwtConverter(twitterQueryUserDetailsService);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(pathsToIgnore);
    }

//        Commented below while implementing Resource Server KeyCloak
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
////        auth.inMemoryAuthentication().withUser("test").password("{noop}test1234").roles("USER");
//        auth.inMemoryAuthentication().withUser(userConfigData.getUserName()).
//                password(passwordEncoder().encode(userConfigData.getPassword())).
//                roles(userConfigData.getRoles());
//    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
