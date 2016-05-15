package de.bvb.study.util;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtil extends BaseUtil {
    /**
     * 关闭 Closeable 对象
     *
     * @param closeable
     */
    public static void closeCloseable(Closeable... closeable) {
        try {
            if (closeable == null || closeable.length <= 0) return;
            for (Closeable c : closeable) {
                if (c != null) c.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
