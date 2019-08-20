package com.lx.demo.springbootmemcached;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private MemcachedClient memcachedClient;

	@Test
	public void testSetGet()  {
		memcachedClient.set("testkey",1000,"666666");
		log.info("***********{}",memcachedClient.get("testkey").toString());
	}
}
