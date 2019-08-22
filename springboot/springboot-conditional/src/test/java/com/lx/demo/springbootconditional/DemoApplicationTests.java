package com.lx.demo.springbootconditional;

import com.lx.demo.springbootconditional.service.OSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private OSService osService;

	@Test
	public void contextLoads() {
		osService.echo();
	}

}
