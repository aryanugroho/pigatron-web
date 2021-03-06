package com.pigatron.web.cms.image.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.pigatron.web.core.api.View;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Document
public class Image {

    @Id
    @JsonView(View.Public.class)
    private String id;

    @JsonIgnore
    private byte[] fileData;

    @JsonIgnore
    private byte[] hash;

    @JsonIgnore
    private String mimeType;

    @JsonView(View.Public.class)
    private String text;

    @JsonIgnore
    private Map<String, byte[]> resizedImages;


    public Image() {
        this.resizedImages = new HashMap<>();
    }

    public Image(byte[] fileData, String mimeType) {
        this();
        this.fileData = fileData;
        this.mimeType = mimeType;
        this.hash = calculateHash(fileData);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    private byte[] calculateHash(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, byte[]> getResizedImages() {
        return resizedImages;
    }

    public void setResizedImages(Map<String, byte[]> resizedImages) {
        this.resizedImages = resizedImages;
    }

    public Optional<Image> findResizedImage(Integer width, Integer height) {
        String key = createResizedImageKey(width, height);
        byte[] bytes = resizedImages.get(key);
        if(bytes != null) {
            return Optional.of(new Image(bytes, mimeType));
        } else {
            return Optional.empty();
        }
    }

    public void storeResizedImage(Image resizedImage, Integer width, Integer height) {
        String key = createResizedImageKey(width, height);
        resizedImages.put(key, resizedImage.getFileData());
    }

    public void removeResizedImages() {
        resizedImages.clear();
    }

    private String createResizedImageKey(Integer width, Integer height) {
        return width + "x" + height;
    }

    public int getFileSize() {
        return fileData.length;
    }

    public int getCachedFilesSize() {
        int size = 0;
        for(byte[] data : resizedImages.values()) {
            size += data.length;
        }
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;

        return (this.fileData.length == image.fileData.length &&
                Arrays.equals(this.hash, image.hash) &&
                Arrays.equals(this.fileData, image.fileData));

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hash);
    }
}
