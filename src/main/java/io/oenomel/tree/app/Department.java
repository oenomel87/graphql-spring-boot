package io.oenomel.tree.app;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = 5768473469636764187L;

    private Long deptId;

    private String name;

    private String deptPathId;

    private Long parentDeptId;

    private String fromDate;

    private String toDate;

    private List<Department> childDepartments;

    public static Department convert(DepartmentEntity e) {
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var from = e.getFromDate().format(format);
        var to = e.getToDate() != null ? e.getToDate().format(format) : null;
        return Department.builder()
                .deptId(e.getDeptId())
                .deptPathId(e.getDeptPathId())
                .name(e.getName())
                .parentDeptId(e.getParentDeptId())
                .fromDate(from)
                .toDate(to)
                .build();
    }
}
