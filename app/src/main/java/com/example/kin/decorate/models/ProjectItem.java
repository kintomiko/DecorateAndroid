package com.example.kin.decorate.models;

import com.example.kin.decorate.common.enums.ProjectItemStatus;
import com.example.kin.decorate.common.enums.ProjectItemType;

/**
 * Created by kindai on 15/3/18.
 */
public class ProjectItem {
    public Integer id;
    public String desc;
    public ProjectItemStatus status;
    public ProjectItemType type;

    @Override
    public String toString() {
        return desc +
                " 类型：" + type.value +
                " 状态：" + status.value
                ;
    }

    public ProjectItem(Integer id, String desc, ProjectItemStatus status, ProjectItemType type) {
        this.id=id;
        this.desc = desc;
        this.status = status;
        this.type = type;
    }
}
