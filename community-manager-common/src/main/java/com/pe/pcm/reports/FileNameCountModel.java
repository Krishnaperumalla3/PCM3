package com.pe.pcm.reports;

public class FileNameCountModel {

    private String name;
    private String count;

    public FileNameCountModel(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FileNameCountModel{" +
                "name='" + name + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
    
}
