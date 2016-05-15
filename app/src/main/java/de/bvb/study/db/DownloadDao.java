package de.bvb.study.db;

import java.util.List;

import de.bvb.study.entity.download.ThreadInfo;

public interface DownloadDao {
    void insert(ThreadInfo threadInfo);

    void delete(String url, int threadId);

    void update(String url, int threadId, long finished);

    List<ThreadInfo> getThreadInfoList(String url);

    boolean isExists(String url, int threadId);
}
