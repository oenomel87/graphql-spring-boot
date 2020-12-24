package io.oenomel.tree.app;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "department")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity implements Serializable {

    private static final long serialVersionUID = 5174070050695636332L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "dept_code")
    private String deptCode;

    private String name;

    @Column(name = "parent_dept_id")
    private Long parentDeptId;

    @Column(name = "parent_dept_code")
    private String parentDeptCode;

    @Column(name = "dept_path_id")
    private String deptPathId;

    @Column(name = "dept_path_name")
    private String deptPathName;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
