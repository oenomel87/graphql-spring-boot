package io.oenomel.tree.app;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = 5768473469636764187L;

    private Long deptId;

    private String deptCode;

    private String name;

    private String deptPathId;

    private Long parentDeptId;

    private String parentDeptCode;

    private LocalDate fromDate;

    private LocalDate toDate;

    private List<Department> childDepartments;

    public static Department convert(DepartmentEntity e) {
        return Department.builder()
                .deptId(e.getDeptId())
                .deptCode(e.getDeptCode())
                .deptPathId(e.getDeptPathId())
                .name(e.getName())
                .parentDeptId(e.getParentDeptId())
                .parentDeptCode(e.getDeptCode())
                .fromDate(e.getFromDate())
                .toDate(e.getToDate())
                .build();
    }
}
