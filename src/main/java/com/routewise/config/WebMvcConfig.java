package com.routewise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/search").setViewName("forward:/search.html");
        registry.addViewController("/seat-selection").setViewName("forward:/seat-selection.html");
        registry.addViewController("/seat-selection/**").setViewName("forward:/seat-selection.html");
        registry.addViewController("/booking-confirmation").setViewName("forward:/confirmation.html");
        registry.addViewController("/booking-confirmation/**").setViewName("forward:/confirmation.html");
        registry.addViewController("/pnr-status").setViewName("forward:/pnr-status.html");
        registry.addViewController("/routes").setViewName("forward:/routes.html");
        registry.addViewController("/login").setViewName("forward:/login.html");
        registry.addViewController("/register").setViewName("forward:/register.html");
        registry.addViewController("/passenger/dashboard").setViewName("forward:/passenger-dashboard.html");
        registry.addViewController("/admin/dashboard").setViewName("forward:/admin-dashboard.html");
        registry.addViewController("/admin/buses").setViewName("forward:/admin-buses.html");
        registry.addViewController("/admin/routes").setViewName("forward:/admin-routes.html");
        registry.addViewController("/admin/schedules").setViewName("forward:/admin-schedules.html");
        registry.addViewController("/admin/bookings").setViewName("forward:/admin-bookings.html");
        registry.addViewController("/admin/waiting-list").setViewName("forward:/admin-waiting-list.html");
        registry.addViewController("/admin/optimizer").setViewName("forward:/admin-optimizer.html");
    }
}
