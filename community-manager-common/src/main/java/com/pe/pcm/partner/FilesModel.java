package com.pe.pcm.partner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * @author Shameer.v.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilesModel implements Serializable {

    private String fileName;
    private String fileSizeInBytes;

    public FilesModel() {
    }

    public FilesModel(String fileName, String fileSizeInBytes) {
        this.fileName = fileName;
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public FilesModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public FilesModel setFileSizeInBytes(String fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
        return this;
    }
}
