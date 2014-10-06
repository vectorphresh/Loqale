package net.nfiniteloop.loqale.backend;


import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vaek on 10/5/14.
 */
@Entity
public class Tag {
    @Id
    private Long id;
    @Index
    private String tagId;
    private GeoPt location;
    private String labels;
    private String text;
    private String url;
    private Blob image;
    private Date createDate;

    public String getTagId() {
        return tagId;
    }

    public GeoPt getLocation() {
        return location;
    }

    public String getLabels() {
        return labels;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public Blob getImage() {
        return image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setLocation(GeoPt location) {
        this.location = location;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

