package io.oenomel.tree.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>, JpaSpecificationExecutor<DepartmentEntity>, DepartmentCustomRepository {

    List<DepartmentEntity> findByDeptIdOrDeptCodeOrName(Long deptId, String deptCode, String name);

    List<DepartmentEntity> findByParentDeptId(Long parentDeptId);
}
