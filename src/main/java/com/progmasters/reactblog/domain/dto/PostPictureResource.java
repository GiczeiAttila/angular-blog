package com.progmasters.reactblog.domain.dto;

public class PostPictureResource {
    private byte[] data;
    private String mediaType;
    private String originalFileName;

    public PostPictureResource() {

    }
    public PostPictureResource(byte[] data, String mediaType, String originalFileName) {
        this.data = data;
        this.mediaType = mediaType;
        this.originalFileName = originalFileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
