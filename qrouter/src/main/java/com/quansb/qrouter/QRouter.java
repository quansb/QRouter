package com.quansb.qrouter;

/**
 * @author quanshibao
 * @description
 * @date 2020/11/3
 */
public class QRouter {

    private static volatile QRouter qRouter;

    private QRouter() {}

    public static QRouter getInstance() {
        if (null == qRouter) {
            synchronized (QRouter.class) {
                if (null == qRouter) {
                    qRouter = new QRouter();
                }
            }
        }
        return qRouter;
    }

    public QParcel build(){
        return new QParcel();
    }

}
