package io.oenomel.tree.app;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCriteria implements Serializable {

    private static final long serialVersionUID = 6709747870689690826L;

    private Long deptId;

    private String deptCode;

    private String name;

    private Long parentDeptId;

    private String parentDeptCode;

    private String fromDate;

    private String toDate;
}
