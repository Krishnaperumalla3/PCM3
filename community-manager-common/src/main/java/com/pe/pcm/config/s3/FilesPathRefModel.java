package com.pe.pcm.config.s3;

/**
 * @author Kiran Reddy.
 */
public class FilesPathRefModel {

    private String sourcePath;
    private String destPath;

    public String getSourcePath() {
        return sourcePath;
    }

    public FilesPathRefModel setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    public String getDestPath() {
        return destPath;
    }

    public FilesPathRefModel setDestPath(String destPath) {
        this.destPath = destPath;
        return this;
    }
}
