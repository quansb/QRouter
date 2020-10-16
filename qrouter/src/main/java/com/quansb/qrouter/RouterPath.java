package com.quansb.qrouter;
import java.util.Map;

/**
 * author:quansb
 * Description:
 * Date:2020/5/18
 */
public interface RouterPath {
    public Map<String, Class<?>> getPathMap();
}
