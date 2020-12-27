package io.oenomel.tree.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import io.oenomel.tree.app.Department;
import io.oenomel.tree.app.DepartmentCriteria;
import io.oenomel.tree.app.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final DepartmentService departmentService;

    public List<Department> getDepartments(Long deptId,
                                           String name,
                                           Long parentDeptId) {
        var criteria = DepartmentCriteria.builder()
                .deptId(deptId)
                .name(name)
                .parentDeptId(parentDeptId)
                .build();
        return this.departmentService.getDepartments(criteria);
    }

    public Department getDepartment(Long deptId) {
        return this.departmentService.getDepartment(deptId);
    }
}
