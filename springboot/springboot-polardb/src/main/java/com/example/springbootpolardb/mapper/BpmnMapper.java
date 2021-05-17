package com.example.springbootpolardb.mapper;

import com.example.springbootpolardb.domain.BpmnTemplateDef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 如果想通过spring注入还是需要假如mapper注解或者配置扫描mapperscan
 */
@Mapper
public interface BpmnMapper {
    List<BpmnTemplateDef> selectTemplateDefByCategoryAndState(Map<String, Object> var1);
}