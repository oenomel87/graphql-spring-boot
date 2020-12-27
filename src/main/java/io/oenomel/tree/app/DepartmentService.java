package io.oenomel.tree.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getDepartments(DepartmentCriteria criteria) {
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

        var childDepartments = childDepartmentEntities.stream()
                .map(Department::convert)
                .collect(Collectors.toList());
        for(var child : childDepartments) {
            this.setChildDepartments(child);
        }
        d.setChildDepartments(childDepartments);
    }

    @Transactional(rollbackFor = Exception.class)
    public Department createDepartment(DepartmentEntity e) {
        this.departmentRepository.save(e);
        this.setDeptPath(e);
        this.departmentRepository.save(e);

        return Department.convert(e);
    }

    private void setDeptPath(DepartmentEntity d) {
        var parent = d.getParentDeptId() == null ? null
                : this.departmentRepository.findById(d.getDeptId()).orElse(null);
        if(parent == null) {
            d.setParentDeptId(null);
            d.setDeptPathId(d.getDeptId() + "");
            d.setDeptPathName(d.getName());
            return;
        }

        d.setParentDeptId(parent.getDeptId());
        d.setDeptPathId(String.format("%s/%s", parent.getDeptPathId(), d.getDeptId()));
        d.setDeptPathName(String.format("%s/%s", parent.getDeptPathName(), d.getName()));
    }

    public Department changeDepartment(DepartmentEntity e) {
        var d = this.departmentRepository.findById(e.getDeptId());
        if(d.isEmpty()) {
            return null;
        }

        var dept = d.get();
        dept.setName(e.getName());
        dept.setFromDate(e.getFromDate());
        dept.setToDate(e.getToDate());
        this.setDeptPath(dept);
        this.departmentRepository.save(dept);
        return Department.convert(dept);
    }
}
