package io.oenomel.tree.config.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import io.oenomel.tree.app.Department;
import io.oenomel.tree.app.DepartmentCriteria;
import io.oenomel.tree.app.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final DepartmentRepository departmentRepository;

    public List<Department> getDepartments(Long deptId,
                                           String deptCode,
                                           String name,
                                           Long parentDeptId) {
        var criteria = DepartmentCriteria.builder()
                .deptId(deptId)
                .deptCode(deptCode)
                .name(name)
                .parentDeptId(parentDeptId)
                .build();
        var departmentEntities = this.departmentRepository.findDepartments(criteria);
        var departments = departmentEntities.stream().map(Department::convert).collect(Collectors.toList());
        departments.forEach(this::setChildDepartments);
        return departments;
    }

    public Department getDepartment(Long deptId) {
        var departmentEntity = this.departmentRepository.findById(deptId);
        return departmentEntity.map(Department::convert).orElse(null);
    }

    private void setChildDepartments(Department d) {
        var childDepartmentEntities = this.departmentRepository.findByParentDeptId(d.getDeptId());
        if(childDepartmentEntities.isEmpty()) {
            return;
        }

        var childDepartments = childDepartmentEntities.stream().map(Department::convert).collect(Collectors.toList());
        for(var child : childDepartments) {
            this.setChildDepartments(child);
        }
        d.setChildDepartments(childDepartments);
    }
}
