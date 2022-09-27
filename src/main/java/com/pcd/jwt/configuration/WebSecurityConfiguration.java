package com.pcd.jwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailsService jwtService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors();
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate", "/registerNewStudent","/registerNewFormer","/registerNewCenter",
                        "/list","/user/{Id}","/userEmail/{userEmail}","/delete-user/{id}","/roles/{roleName}",
                        "/update/{UserId}","/addCourses","/courses","/course/{id}","/category/{category}","/email/{formerEmail}","/delete-course/{id}",
                        "/update-course/{id}","/city/{city}","/courses/count","/users/count/{roleName}","/profile/{userEmail}","/users/update/{userName}",
                        "/updateCourses","/distinct-former","/picture/{formerEmail}","/favoriteCourses/{isFavorite}","/Favorite/{id}",
                        "/allFavorite","/first5Favorite/{first5Favorite}","/CourseName/{CourseName}/{FormerEmail}","/addCenterCourses",
                        "/addAnnouncement","/announcements","/centerCategory/{category}","/saveImage/{userEmail}","/getImageByEmail/{userEmail}",
                        "/Favorite/{id}/{user}","/Favorite/{id}/{user}/{isFavorite}", "/courseUser/{course_id}/{user_id}","/courseByUser/{user_id}",
                        "/centerCourseByUser/{user_id}","/centerCourseUser/{centerCourse_id}/{user_id}","/centerCity/{city}",
                        "/centerFormerEmail/{formerEmail}","/delete-centerCourse/{id}","/delete-announcement/{id}","/isConfirmed/{id}","/centerCourses","/UserByCourses/{id}"


                ).permitAll()
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }
}
