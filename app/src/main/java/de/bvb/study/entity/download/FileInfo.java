package de.bvb.study.entity.download;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/14.
 */
public class FileInfo implements Serializable {
    public int id;
    public String fileName;
    public String url;
    public long fileLength;
    public long finished;

    public FileInfo() {
    }

    public FileInfo(int id, String fileName, String url, long fileLength, long finished) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.fileLength = fileLength;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "threadId=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", fileLength=" + fileLength +
                ", finished=" + finished +
                '}';
    }
}
