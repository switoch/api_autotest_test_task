package entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String name;
    private int id;
    private String status;
    private List<String> photoUrls = new ArrayList<>();
    private List<Tags> tags = new ArrayList<>();
    private Category category;

    public Pet(String name, int id, String status, List<String> photoUrl, List<Tags> tags, Category category) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.photoUrls = photoUrl;
        this.tags = tags;
        this.category = category;
    }

    public Pet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhotoUrl(int index) {
        return photoUrls.get(index);
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrls.add(photoUrl);
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getInfo() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public static class Tags {
        private String name;
        private int id;

        public Tags() {
        }

        public Tags(String tagName, int id) {
            this.name = tagName;
            this.id = id;
        }

        public String getTagName() {
            return name;
        }

        public int getTagId() {
            return id;
        }
    }

    public static class Category {
        private String name;
        private int id;

        public Category() {
        }

        public Category(int categoryId, String categoryName) {
            this.name = categoryName;
            this.id = categoryId;
        }

        public String getCategoryName() {
            return name;
        }

        public int getCategoryId() {
            return id;
        }
    }
}
