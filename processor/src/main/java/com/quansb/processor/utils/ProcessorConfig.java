package com.quansb.processor.utils;

public interface ProcessorConfig {
    // 接收参数的TAG标记
    String MODULE_NAME = "moduleName"; //目的是接收 每个module名称
    String APT_PACKAGE = "packageNameForAPT"; //：目的是接收 包名（APT 存放的包名）
    String GROUP_CLASS_NAME = "QRouter$$Group$$";
    String PATH_CLASS_NAME = "QRouter$$Path$$";
}
