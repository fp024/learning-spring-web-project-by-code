package org.fp024.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"org.fp024.service", "org.fp024.aop"})
@EnableAspectJAutoProxy
public class RootConfig {}
