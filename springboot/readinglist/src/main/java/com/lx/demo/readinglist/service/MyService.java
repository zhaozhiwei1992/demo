package com.lx.demo.readinglist.service;

import com.lx.demo.readinglist.condition.JdbcTemplateCondition;
import org.springframework.context.annotation.Conditional;

/**
 * if jdbctemcon, load myservice
 */
@Conditional(JdbcTemplateCondition.class)
public class MyService {
}
