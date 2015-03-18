package com.example.kin.decorate.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kindai on 15/3/18.
 */
public enum ProjectItemType {
    LIVINGROOM(101, "客厅"),
    SUBLIVVINGROOM(102, "门厅"),
    MAINBEDROOM(103, "主卧"),
    BEDROOM1(104, "次卧1"),
    BEDROOM2(105, "次卧2"),
    BEDROOM3(106, "次卧3"),
    KITCHEN(107, "厨房"),
    TOILET(108, "厕所"),
    ;

    private static List<ProjectItemType> list = new ArrayList<>();
    static{
        list.add(LIVINGROOM);
        list.add(SUBLIVVINGROOM);
        list.add(MAINBEDROOM);
        list.add(BEDROOM1);
        list.add(BEDROOM2);
        list.add(BEDROOM3);
        list.add(KITCHEN);
        list.add(TOILET);
    }

    public Integer id;
    public String value;

    private ProjectItemType(Integer id, String value){
        this.id=id;
        this.value = value;
    }

    public static ProjectItemType getById(Integer id){
        for(ProjectItemType item:list){
            if(item.id.equals(id))
                return item;
        }
        return null;
    }
}
