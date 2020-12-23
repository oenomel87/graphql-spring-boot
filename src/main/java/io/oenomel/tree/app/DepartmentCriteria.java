package io.oenomel.tree.app;

import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentCriteria implements Serializable {

    private static final long serialVersionUID = 6709747870689690826L;

    private Long deptId;

    private String deptCode;

    private String name;
}
