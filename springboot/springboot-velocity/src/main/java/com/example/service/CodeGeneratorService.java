package com.example.service;

import com.example.utils.GenUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class CodeGeneratorService {

	public Map<String, String> queryTable() {
		//table_name tableName, engine, table_comment tableComment, create_time createTime
		final HashMap<String, String> map = new HashMap<>();
		map.put("tableName", "t_example");
		map.put("tableComment", "测试代码生成");
		return map;
	}

	public List<Map<String, String>> queryColumns() {
		//column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		final Map<String, String> idMap = new HashMap<>();
		idMap.put("columnName", "id");
		idMap.put("dataType", "varchar");
		idMap.put("columnComent", "编码");
		list.add(idMap);
		final Map<String, String> nameMap = new HashMap<>();
		nameMap.put("columnName", "name");
		nameMap.put("dataType", "varchar");
		nameMap.put("columnComent", "姓名");
		list.add(nameMap);
		final Map<String, String> ageMap = new HashMap<>();
		ageMap.put("columnName", "age");
		ageMap.put("dataType", "varchar");
		ageMap.put("columnComent", "年龄");
		list.add(ageMap);
		return list;
	}

	/**
	 * @data: 2023/3/11-下午5:19
	 * @User: zhaozhiwei
	 * @method: generatorCode
	 * @return: void
	 * @Description: 代码生产可以写到本地文件, 测试可以先输出即可
	 */
	public byte[] generatorCode() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		Map<String, String> table = queryTable();
		List<Map<String, String>> columns = queryColumns();
		//生成代码
		GenUtils.generatorCode(table, columns, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
