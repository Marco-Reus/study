package de.bvb.study.entity.download;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/14.
 */
public class ThreadInfo implements Serializable {

    public int threadId;
    public String url;
    public long start;
    public long end;
    public long finished;

    public ThreadInfo() {
    }

    public ThreadInfo(int threadId, String url, long start, long end, long finished) {
        this.threadId = threadId;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "threadId=" + threadId +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
