package com.example.demo.comment;

public class SessUtil {
    private static ThreadLocal<SessBean> tl = new ThreadLocal<SessBean>();

    public static void setSess(SessBean bean) {
        tl.set(bean);
    }

    public static SessBean getSess() {
        return tl.get();
    }
}
