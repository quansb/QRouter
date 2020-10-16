package com.app.routers;

import java.util.Map;

/**
 * author:quansb
 * Description:
 * Date:2020/5/18
 */
public interface RouterGroup {
    public Map<String, Class<? extends RouterPath>> getGroupMap();
}
