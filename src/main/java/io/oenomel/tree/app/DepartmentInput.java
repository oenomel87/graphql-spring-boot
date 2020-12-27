package io.oenomel.tree.app;

import lombok.*;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentInput implements Serializable {

    private static final long serialVersionUID = -4179737074863830554L;

    private Long deptId;

    private String name;

    private String deptPathId;

    private Long parentDeptId;

    private String fromDate;

    private String toDate;

    public static DepartmentInput convert(DepartmentEntity e) {
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var from = e.getFromDate().format(format);
        var to = e.getToDate() != null ? e.getToDate().format(format) : null;
        return DepartmentInput.builder()
                .deptId(e.getDeptId())
                .deptPathId(e.getDeptPathId())
                .name(e.getName())
                .parentDeptId(e.getParentDeptId())
                .fromDate(from)
                .toDate(to)
                .build();
    }
}
