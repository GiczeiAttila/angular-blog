package com.progmasters.reactblog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.reactblog.service.cloudinary.UploadResponse;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * We only store file name's and other data in db table, not the file content itself!
 */
@Entity
@Table(name = "file_registry")
public class PostPictureRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_path")
    private String filePath;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "category")
    private String category = "default";
    @Column(name = "upload_datetime")
    @JsonFormat(locale = "hu", shape = JsonFormat.Shape.STRING, pattern = "yyyy. MM. dd. HH:mm:ss (Z)")
    private ZonedDateTime uploadDateTime = ZonedDateTime.now();
    @OneToOne(mappedBy = "picture")
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public PostPictureRegistry() {

    }

    public PostPictureRegistry(UploadResponse uploadResponse, CommonsMultipartFile commonsMultipartFile) {
        this.filePath = uploadResponse.getSecureUrl();
        this.originalFileName = commonsMultipartFile.getFileItem().getName();
        this.fileSize = uploadResponse.getBytes();
        this.mediaType = commonsMultipartFile.getContentType();
    }

    public PostPictureRegistry(String fullFilePath, long size, String contentType, String originalFileName) {
        this.filePath = fullFilePath;
        this.fileSize = size;
        this.mediaType = contentType;
        this.originalFileName = originalFileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ZonedDateTime getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(ZonedDateTime uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }
}
