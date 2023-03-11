/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.controller;

import com.example.service.CodeGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码生成器
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Controller
@RequestMapping("/generator")
public class CodeGeneratorController {
	@Autowired
	private CodeGeneratorService sysGeneratorService;
	
	/**
	 * 生成代码
	 * 浏览器访问 http://127.0.0.1:8080/generator/code, 即可下载
	 */
	@RequestMapping("/code")
	public void code(String tables, HttpServletResponse response) throws IOException{
		byte[] data = sysGeneratorService.generatorCode();
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());
	}
}
