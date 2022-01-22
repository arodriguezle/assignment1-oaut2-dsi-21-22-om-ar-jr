package cat.tecnocampus.assignment1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final OAuth2ClientContext oauth2ClientContext;
    @Value("${github.resource.userInfoUri}")
    private String resourceUserInfoUri;

    public SecurityConfig(OAuth2ClientContext oauth2ClientContext) {
        this.oauth2ClientContext = oauth2ClientContext;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll()
                .anyRequest().authenticated().and().csrf().disable()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class
                );
        // @formatter:on
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter gitHubFilter = new OAuth2ClientAuthenticationProcessingFilter(
                "/login/gitHub");
        OAuth2RestTemplate gitHubTemplate = new OAuth2RestTemplate(gitHub(), oauth2ClientContext);
        gitHubFilter.setRestTemplate(gitHubTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceUserInfoUri,
                gitHub().getClientId());
        tokenServices.setRestTemplate(gitHubTemplate);
        gitHubFilter.setTokenServices(
                new UserInfoTokenServices(resourceUserInfoUri, gitHub().getClientId()));
        return gitHubFilter;
    }

    @Bean
    public OAuth2RestTemplate gitHubRestTemplate() {
        return new OAuth2RestTemplate(gitHub(), oauth2ClientContext);
    }

    @Bean
    @ConfigurationProperties("github.client")
    public AuthorizationCodeResourceDetails gitHub() {
        return new AuthorizationCodeResourceDetails();
    }
}
