package com.lx.demo.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: LRUCache
 * @Package com/lx/demo/LRUCache.java
 * @Description: 设定最大缓存空间 MAX_ENTRIES 为 3；
 *     使用 LinkedHashMap 的构造函数将 accessOrder 设置为 true，开启 LRU 顺序；
 *     覆盖 removeEldestEntry() 方法实现，在节点多于 MAX_ENTRIES 就会将最近最久未使用的数据移除。
 * @author zhaozhiwei
 * @date 2021/3/17 下午3:14
 * @version V1.0
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final int MAX_ENTRIES = 3;

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;
    }

    LRUCache() {
        super(MAX_ENTRIES, 0.75f, true);
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>();
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.get(1);
        cache.put(4, "d");
        //[3, 1, 4], 总共只能放3个， 2属于没有使用过的
        System.out.println(cache.keySet());
    }
}