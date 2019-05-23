package com.sap.data;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;


public class Book {
    private SimpleStringProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty publishedDate;
    private SimpleStringProperty lentBy;
    private SimpleBooleanProperty isAvailable;



    public Book(String id,String title,  String author, String publishedDate, String lentBy,
                Boolean isAvailable) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.publishedDate = new SimpleStringProperty(publishedDate);
        this.lentBy = new SimpleStringProperty(lentBy);
        this.isAvailable = new SimpleBooleanProperty(isAvailable);
    }
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }


    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getPublishedDate() {
        return publishedDate.get();
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate.set(publishedDate);
    }

    public String getLentBy() {
        return lentBy.get();
    }

    public void setLentBy(String lentBy) {
        this.lentBy.set(lentBy);
    }

    public boolean isIsAvailable() {
        return isAvailable.get();
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }
}

