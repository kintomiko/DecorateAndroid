package com.example.kin.decorate.models;

/**
 * Created by kindai on 15/3/18.
 */
public class Project {
    public String desc;
    public Integer id;

    public Project(String desc, Integer id) {
        this.desc = desc;
        this.id = id;
    }

    @Override
    public String toString() {
        return "工程项目：" + desc ;
    }
}
