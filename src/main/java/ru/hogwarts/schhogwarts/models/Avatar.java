package ru.hogwarts.schhogwarts.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity

public class Avatar {
    @Id
    @GeneratedValue
    private Long Id;
    private String filePath;
    private Long fileSize;
    private String mediaType;
    private byte[] data;
    @OneToOne
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Avatar(Long id, String filePath, Long fileSize, String mediaType, byte[] data, Student student) {
        Id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
        this.student = student;
    }
    public Avatar() {

    }
}
