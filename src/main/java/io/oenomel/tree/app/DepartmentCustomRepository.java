package io.oenomel.tree.app;

import java.util.List;

public interface DepartmentCustomRepository {

    List<DepartmentEntity> findDepartments(DepartmentCriteria criteria);
}
