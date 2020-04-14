package com.banuacoders.pico.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Posts")
public class DistrictPost {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int no;
    private String name;
    private String posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }
}
