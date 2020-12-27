package io.oenomel.tree.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import io.oenomel.tree.app.Department;
import io.oenomel.tree.app.DepartmentEntity;
import io.oenomel.tree.app.DepartmentInput;
import io.oenomel.tree.app.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final DepartmentService departmentService;

    public Department saveDepartment(DepartmentInput department) {
        var departmentEntity = DepartmentEntity.builder()
                .deptId(department.getDeptId())
                .name(department.getName())
                .parentDeptId(department.getParentDeptId())
                .fromDate(this.convertStringToDate(department.getFromDate()))
                .toDate(this.convertStringToDate(department.getToDate()))
                .build();
        if(departmentEntity.getDeptId() != null) {
            return this.departmentService.changeDepartment(departmentEntity);
        }
        return this.departmentService.createDepartment(departmentEntity);
    }

    private LocalDate convertStringToDate(String date) {
        var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date != null && !date.isEmpty()
                ? LocalDate.parse(date, dateFormat) : null;
    }
}
