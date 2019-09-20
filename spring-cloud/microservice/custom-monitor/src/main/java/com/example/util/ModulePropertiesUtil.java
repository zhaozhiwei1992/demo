package com.example.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.util.LoaderUtil;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @author qiu
 */
public class ModulePropertiesUtil {

    protected final static Log logger = LogFactory.getLog(ModulePropertiesUtil.class);
    /**
     * Resource name for a Log4j 2 provider properties file.
     */
    protected static final String MODULE_PROPERTIES_RESOURCE = "module.properties";

    protected static final String MODULE_NAME = "module.name";

    protected static final String MODULE_DEPENDENCIES = "module.dependencies";

    protected static final String MODULE_XMLRESOURCE_APPIDS = "module.xmlresource.appids";

    protected static final String MODULE_XMLRESOURCE_INCLUDES = "module.xmlresource.includes";

    protected static final String MODULE_XMLRESOURCE_EXCLUDES = "module.xmlresource.excludes";

    protected static final Map<String,ModuleProperties> modulePropertiesMap = new HashMap<String,ModuleProperties>();

    private static  final String DEFAULT_DEPENDENCIES = "ifmis-framework-core,ifmis-framework-public";

    protected static final Set<String> moduleAppids = new LinkedHashSet<>();

    protected static final Set<String> moduleXmlRources = new LinkedHashSet<>();


    static class ModuleProperties{
        private String name;
        private String dependencies;
        private Set<String> appids;
        private Set<String> includes;
        private Set<String> excludes;

        public ModuleProperties(String name) {
            this.name = name;
        }

        public ModuleProperties(String name, String dependencies) {
            this.name = name;
            this.dependencies = dependencies;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDependencies() {
            return dependencies;
        }

        public void setDependencies(String dependencies) {
            this.dependencies = dependencies;
        }

        public Set<String> getAppids() {
            return appids;
        }

        public void setAppids(Set<String> appids) {
            this.appids = appids;
        }

        public Set<String> getIncludes() {
            return includes;
        }

        public void setIncludes(Set<String> includes) {
            this.includes = includes;
        }

        public Set<String> getExcludes() {
            return excludes;
        }

        public void setExcludes(Set<String> excludes) {
            this.excludes = excludes;
        }

        public Set<String> getXmlResources(){
            Set<String> set = new LinkedHashSet<>();
            for(String appid : getAppids()){
                if(!set.contains( appid + "-context.xml")){
                    set.add( appid + "-context.xml");
                    set.add( appid + "-appupgrade.xml");
                    if(!set.contains( appid + "-context-server.xml")) {
                        set.add(appid + "-context-server.xml");
                    }
                }
            }
            Set<String> xmlrources  = this.getIncludes();
            for(String xml : xmlrources) {
                if(!set.contains( xml) ){
                    set.add(xml);
                }
            }
            return set;
        }
        public Set<String> excludesXmlRources(Set<String> xmlrources){
            if(!this.excludes.isEmpty()){
                Iterator<String> it  = xmlrources.iterator();
                while (it.hasNext()){
                    if(this.excludes.contains(it.next())){
                        it.remove();
                    }
                }
            }
            return xmlrources;
        }
    }


    private static void initModuleProperties(){
        if(modulePropertiesMap.isEmpty()) {
            Collection<URL> resources = new LinkedHashSet<>();
            resources.addAll(LoaderUtil.findResources(MODULE_PROPERTIES_RESOURCE));
            logger.info("读取了"+resources.size()+" module.properties");
            for (final URL resource : resources) {
                Properties props = loadProperties(resource, resource.getClass().getClassLoader());
                logger.info("读取"+resource.getFile());
                if (props != null) {
                    String moduleName = props.getProperty(MODULE_NAME);
                    String dependencies = props.getProperty(MODULE_DEPENDENCIES);
                    ModuleProperties moduleProperties = new ModuleProperties(moduleName, dependencies);
                    moduleProperties.setAppids(getPropertiesSet(props, MODULE_XMLRESOURCE_APPIDS));
                    moduleProperties.setIncludes(getPropertiesSet(props, MODULE_XMLRESOURCE_INCLUDES));
                    moduleProperties.setExcludes(getPropertiesSet(props, MODULE_XMLRESOURCE_EXCLUDES));
                    modulePropertiesMap.put(moduleName,moduleProperties);
                }else {
                    logger.info("读取失败"+resource.getFile());
                }
            }
            if(!modulePropertiesMap.containsKey(SystemEnvironment.getApplicationName())){
                String moduleName = SystemEnvironment.getApplicationName();
                ModuleProperties moduleProperties = new ModuleProperties(moduleName);
                String[] strings = moduleName.split("-");
                if(strings.length>1){
                    String appid = strings[1];
                    Set<String> set = new LinkedHashSet<>();
                    set.add(appid);
                    moduleProperties.setAppids(set);
                }
            }

            for(ModuleProperties moduleProperties:modulePropertiesMap.values()){
                for (String appid : moduleProperties.getAppids()){
                    if(!moduleAppids.contains(appid)){
                        moduleAppids.add(appid);
                    }
                }
                Set<String> dependencies = parseDepthDependencies(moduleProperties.getDependencies());
                String[] dependenciesArray = StringUtils.toStringArray(dependencies);
                for(int i = dependenciesArray.length -1;i>=0; i--){
                    String modulename = dependenciesArray[i];
                    ModuleProperties childModuleProperties = modulePropertiesMap.get(modulename);
                    if(childModuleProperties!=null) {
                        moduleXmlRources.addAll(childModuleProperties.getXmlResources());
                        childModuleProperties.excludesXmlRources(moduleXmlRources);
                    }else{
                        logger.warn(modulename+"无此依赖模块……");
                    }
                }
                moduleXmlRources.addAll(moduleProperties.getXmlResources());
                moduleProperties.excludesXmlRources(moduleXmlRources);
            }
            String glEnable = SystemEnvironment.getProperty("ifmis.fasp2.gl.enable");
            boolean fasp2Gl = false;
            try {
                fasp2Gl = glEnable == null ? false : Boolean.valueOf(glEnable);
            } catch(Exception ex) {
            }
            if (fasp2Gl) {
                moduleXmlRources.add("fasp2-gl-context.xml");
            }
        }
    }

    private static Set<String> parseDepthDependencies(String dependencies){
        Set<String> set;
        if(StringUtils.isEmpty(dependencies)){
            set =  StringUtils.commaDelimitedListToSet(DEFAULT_DEPENDENCIES);
        }else{
            set =  StringUtils.commaDelimitedListToSet(dependencies);
            String[] dependenciesArray = StringUtils.toStringArray(set);
            for(int i = dependenciesArray.length -1;i>=0; i--){
                String modulename = dependenciesArray[i];
                ModuleProperties moduleProperties = modulePropertiesMap.get(modulename);
                if(moduleProperties!=null && !StringUtils.isEmpty(moduleProperties.getDependencies())){
                    Set<String> children = parseDepthDependencies(moduleProperties.getDependencies());
                    for(String module: children) {
                        if(!set.contains(module)){
                            set.add(module);
                        }
                    }
                }else if(moduleProperties==null ){
                    logger.warn(modulename+"无此依赖模块……");
                };
            }
        }
        return set;
    }



    private static Set<String> getPropertiesSet(Properties props, String key){
        String value = props.getProperty(key);
        return StringUtils.commaDelimitedListToSet(value);
    }



    public static Set<String> getXmlResources(){
        initModuleProperties();
        return moduleXmlRources;
    }

    public static Set<String> getAppids(){
        initModuleProperties();;
        return moduleAppids;
    }

    protected static Properties loadProperties(final URL url, final ClassLoader cl) {
        try {
            return loadClose(url.openStream(), url);
        } catch (final IOException e) {
            logger.error("Unable to open " + url, e);
        }
        return null;
    }

    /**
     * Loads and closes the given property input stream. If an error occurs, log to the status logger.
     *
     * @param in     a property input stream.
     * @param source a source object describing the source, like a resource string or a URL.
     * @return a new Properties object
     */
    static Properties loadClose(final InputStream in, final Object source) {
        final Properties props = new Properties();
        if (null != in) {
            try {
                props.load(in);
            } catch (final IOException e) {
                logger.error("Unable to read " + source, e);
            } finally {
                try {
                    in.close();
                } catch (final IOException e) {
                    logger.error("Unable to close " + source, e);
                }
            }
        }
        return props;
    }
}
