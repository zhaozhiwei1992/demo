package com.example.springbootjpa.repository;

import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.hibernate.transform.BasicTransformerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: oracle clob/blob字段转换兼容器
 * @author: LIUTIE
 * @date: 2017年8月22日 下午12:03:43
 * @version V1.0
 */
public class HibernateAliasToEntityMapResultTransformer extends BasicTransformerAdapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final HibernateAliasToEntityMapResultTransformer INSTANCE = new HibernateAliasToEntityMapResultTransformer();

	/**
	 * Disallow instantiation of AliasToEntityMapResultTransformer.
	 */
	private HibernateAliasToEntityMapResultTransformer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new HashMap(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				if (tuple[i] != null && Proxy.isProxyClass(tuple[i].getClass())) {
					Object proxyObj = Proxy.getInvocationHandler(tuple[i]);
					if (proxyObj instanceof SerializableBlobProxy) {
						SerializableBlobProxy proxy = (SerializableBlobProxy) Proxy.getInvocationHandler(tuple[i]);
						java.sql.Blob blob = proxy.getWrappedBlob();
						InputStream is = null;
						try {
							is = blob.getBinaryStream();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						result.put(alias, is);
					} else if (proxyObj instanceof SerializableClobProxy) {
						SerializableClobProxy proxy = (SerializableClobProxy) Proxy.getInvocationHandler(tuple[i]);
						java.sql.Clob clob = proxy.getWrappedClob();
						String facepicmps = "";
						Reader is = null;
						try {
							StringBuffer sb = new StringBuffer();
							is = clob.getCharacterStream();
							char[] b = new char[60000];// 每次获取60K  
							int k = 0;
							while ((k = is.read(b)) != -1) {
								sb.append(b, 0, k);
							}
							facepicmps = sb.toString();
						} catch (SQLException | IOException e) {
							e.printStackTrace();
						} // 得到流
						finally {
							try {
								if (is != null) {
									is.close();
								}
							} catch (Exception e) {
							}

						}
						result.put(alias, facepicmps);
					}
				} else {
					result.put(alias, tuple[i]);
				}
			}
		}
		return result;
	}

	/**
	 * Serialization hook for ensuring singleton uniqueing.
	 *
	 * @return The singleton instance : {@link #INSTANCE}
	 */
	private Object readResolve() {
		return INSTANCE;
	}
}
