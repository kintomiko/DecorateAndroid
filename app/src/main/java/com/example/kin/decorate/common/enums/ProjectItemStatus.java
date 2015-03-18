package com.example.kin.decorate.common.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kindai on 15/3/18.
 */
public enum ProjectItemStatus implements Serializable{
    FINISHED(203, "完成"),
    DISCUSSING(201, "讨论中"),
    WORKING(202, "进行中"),
    ;

    private static List<ProjectItemStatus> list = new ArrayList<>();
    static{
        list.add(FINISHED);
        list.add(DISCUSSING);
        list.add(WORKING);
    }

    public Integer id;
    public String value;

    private ProjectItemStatus(Integer id, String value){
        this.id=id;
        this.value = value;
    }

    public static ProjectItemStatus getById(Integer id){
        for(ProjectItemStatus item:list){
            if(item.id.equals(id))
                return item;
        }
        return null;
    }
}
