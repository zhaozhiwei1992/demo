package com.lx.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtils {

	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
	private final static ObjectMapper objectMapper;


	static {
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * JSON串转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
	 * 
	 * @param <T>
	 * @param jsonString
	 *            JSON字符串
	 * @param tr
	 *            TypeReference,例如: new TypeReference< List<FamousUser> >(){}
	 * @return List对象列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2GenericObject(String jsonString,
			TypeReference<T> tr) {

		if (jsonString == null || "".equals(jsonString)) {
			return null;
		} else {
			try {
				objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return (T) objectMapper.readValue(jsonString, tr);
			} catch (Exception e) {
				log.warn("json error:" + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Java对象转Json字符串
	 * 
	 * @param object
	 *            Java对象，可以是对象，数组，List,Map等
	 * @return json 字符串
	 */
	@SuppressWarnings("deprecation")
	public static String toJson(Object object) {
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.warn("json error:" + e.getMessage());
		}

		return jsonString;
	}

	/**
	 * Json字符串转Java对象
	 * 
	 * @param jsonString
	 * @param c
	 * @return
	 */
	public static Object json2Object(String jsonString, Class<?> c) {

		if (jsonString == null || "".equals(jsonString)) {
			return "";
		} else {
			try {
				return objectMapper.readValue(jsonString, c);
			} catch (Exception e) {
				log.warn("json error:" + e.getMessage());
			}

		}

		return "";
	}

	/**
	 * 根据json串和节点名返回节点
	 * 
	 * @param json
	 * @param nodeName
	 * @return
	 */
	public static JsonNode getNode(String json, String nodeName) {
		JsonNode node = null;
		try {
			node = JsonUtils.getObjectMapper().readTree(json);
			return node.get(nodeName);
		} catch (JsonProcessingException e) {
			log.warn("json error:" + e.getMessage());
		} catch (IOException e) {
			log.warn("json error:" + e.getMessage());
		}
		return node;
	}

	/**
	 * JsonNode转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
	 * 
	 * @param <T>
	 * @param node
	 *            JsonNode
	 * @param tr
	 *            TypeReference,例如: new TypeReference< List<FamousUser> >(){}
	 * @return List对象列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonNode2GenericObject(JsonNode node,
			TypeReference<T> tr) {
		if (node == null || "".equals(node)) {
			return null;
		} else {
			try {
				return (T) objectMapper.readValue(node, tr);
			} catch (Exception e) {
				log.warn("json error:" + e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 根据json结果获取某个字段的值
	 */
	public static String getValueFromJsonStr(String jsonString, String paramName) {
		if(StringUtils.isNotBlank(jsonString) && StringUtils.isNotBlank(paramName)) {
			try {
				JSONObject jsonObj = JSONObject.parseObject(jsonString);
				if(jsonObj != null) {
					return jsonObj.getString(paramName);
				}
				
			} catch (Exception e) {
				log.error("get param["+paramName+"] value from json["+jsonString+"] error:"+e);
			}
		}
		return null;
	}
	
}
