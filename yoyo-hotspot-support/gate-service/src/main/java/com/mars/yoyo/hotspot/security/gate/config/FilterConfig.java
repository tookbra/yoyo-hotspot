package com.mars.yoyo.hotspot.security.gate.config;

import com.mars.yoyo.hotspot.security.gate.filter.AccessFilter;
import com.mars.yoyo.hotspot.security.gate.filter.IpFilter;
import com.mars.yoyo.hotspot.security.gate.filter.SignFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 过滤器配置
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Configuration
public class FilterConfig {

	@Bean
	public IpFilter ipFilter() {
		return new IpFilter();
	}
	
	@Bean
	public AccessFilter acFilter() {
		return new AccessFilter();
	}
	
	@Bean
	public SignFilter signFilter() {
		return new SignFilter();
	}
}