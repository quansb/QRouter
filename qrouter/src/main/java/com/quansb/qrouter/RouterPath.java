package com.quansb.qrouter;
import java.util.Map;

/**
 * author:quansb
 * Description:
 * Date:2020/5/18
 */
public interface RouterPath {
    Map<String, Class<?>> getPathMap();
}
