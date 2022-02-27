package sun.net.www.protocol.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * 自定义扩展classpath协议uurl访问 , 参考new url实现， 会调用 getURLStreamHandler("协议名称")来获取处理类
 *
 * static final String BUILTIN_HANDLERS_PREFIX = "sun.net.www.protocol";
 *
 * 扩展实现时， 类的命名必须是 BUILTIN_HANDLERS_PREFIX + protocol名称(例如:classpath 适合: classpath://xxxx访问) + Handler.java
 */
public class Handler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        System.out.println("你访问到我了，讨厌");

        //这里只是为了测试，实际应该根据协议解析真是访问路径
        URL resource = Thread.currentThread().getContextClassLoader().getResource("application.properties");
        return resource.openConnection();
    }
}
