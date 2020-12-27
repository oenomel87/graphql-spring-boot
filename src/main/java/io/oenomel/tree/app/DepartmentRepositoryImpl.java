package io.oenomel.tree.app;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import java.util.List;

import static io.oenomel.tree.app.QDepartmentEntity.departmentEntity;

public class DepartmentRepositoryImpl extends QuerydslRepositorySupport implements DepartmentCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final EntityManager entityManager;

    public DepartmentRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        super(DepartmentEntity.class);
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public List<DepartmentEntity> findDepartments(DepartmentCriteria criteria) {
        return this.queryFactory.selectFrom(departmentEntity)
            .where(this.eqDeptId(criteria),
                    this.likeName(criteria),
                    this.eqParentDeptId(criteria))
            .fetch();
    }

    private BooleanExpression likeName(DepartmentCriteria criteria) {
        var name = criteria.getName();
        return name != null && !name.isEmpty() ? departmentEntity.name.like(name) : null;
    }

    private BooleanExpression eqDeptId(DepartmentCriteria criteria) {
        var detpId = criteria.getDeptId();
        return detpId != null ? departmentEntity.deptId.eq(detpId) : null;
    }

    private BooleanExpression eqParentDeptId(DepartmentCriteria criteria) {
        var parentDeptId = criteria.getParentDeptId();
        return parentDeptId != null ? departmentEntity.parentDeptId.eq(parentDeptId) : null;
    }
}
