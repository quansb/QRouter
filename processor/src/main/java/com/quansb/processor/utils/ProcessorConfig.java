package com.quansb.processor.utils;

public interface ProcessorConfig {
    // 接收参数的TAG标记
    String MODULE_NAME = "moduleName"; //目的是接收 每个module名称
    String APT_PACKAGE = "packageNameForAPT"; //：目的是接收 包名（APT生成的java类 存放的包名 存放在哪个目录下面）  //暂时无效
    String GROUP_CLASS_NAME = "QRouter$$Group$$";
    String PATH_CLASS_NAME = "QRouter$$Path$$";


}
