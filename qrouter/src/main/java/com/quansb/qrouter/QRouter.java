package com.quansb.qrouter;

/**
 * @author quanshibao
 * @description
 * @date 2020/11/3
 */
public class QRouter {

    private QRouter() {

    }

    private static class Stub {
        private static QRouter sQRouter = new QRouter();

    }
    public static QRouter getInstance() {
        return Stub.sQRouter;
    }

//    public Parcel with() {
//
//
//    }

    public Parcel build() {
        return new Parcel();
    }

}
