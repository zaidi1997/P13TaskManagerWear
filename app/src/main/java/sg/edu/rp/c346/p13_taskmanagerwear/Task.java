package sg.edu.rp.c346.p13_taskmanagerwear;

import java.io.Serializable;

public class Task implements Serializable {

    private int id;
    private String Title, description;

    public Task(int id, String title, String description) {
        this.id = id;
        Title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  id + " " + Title + '\n' +
               description;
    }
}
