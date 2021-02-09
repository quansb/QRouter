package com.quansb.qrouter;

/**
 * @author quanshibao
 * @description   数据包裹
 * @date 2020/11/3
 */
public interface IParcel<T> {

    T release();

    T obtain();
}
