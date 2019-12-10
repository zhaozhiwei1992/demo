package com.lx.demo.springbooteasyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.lx.demo.springbooteasyexcel.domain.DemoData;
import com.lx.demo.springbooteasyexcel.listener.DemoDataListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	/**
	 * 最简单的读
	 * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
	 * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
	 * <p>3. 直接读即可
	 */
	@Test
	public void simpleRead() throws FileNotFoundException {
		// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
		// 写法1：
		String fileName = ResourceUtils.getFile("classpath").getPath()
				+ "demo" + File.separator + "demo.xlsx";
		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

		// 写法2：
		fileName = ResourceUtils.getFile("classpath").getPath() + "demo" + File.separator + "demo.xlsx";
		ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
		ReadSheet readSheet = EasyExcel.readSheet(0).build();
		excelReader.read(readSheet);
		// 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
		excelReader.finish();
	}

}
