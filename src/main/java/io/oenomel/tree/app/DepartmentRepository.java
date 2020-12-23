package io.oenomel.tree.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    List<DepartmentEntity> findByDeptIdOrDeptCodeOrName(Long deptId, String deptCode, String name);

    List<DepartmentEntity> findByParentDeptId(Long parentDeptId);
}
