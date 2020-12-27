package io.oenomel.tree.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import io.oenomel.tree.app.DepartmentCriteria;
import io.oenomel.tree.app.DepartmentInput;
import io.oenomel.tree.app.DepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutationTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void createDepartmentTest() {
        var response = this.graphQLTestTemplate.postMultipart("mutation SaveDepartment($department: DepartmentInput!){ saveDepartment(department: $department) {name} }", "{\"department\":{\"name\": \"test-dept\", \"fromDate\": \"2019-11-10\"}}");

        this.entityManager.clear();

        var result = this.departmentRepository.findDepartments(DepartmentCriteria.builder().name("test-dept").build());
        Assertions.assertNotNull(response);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void saveDepartmentTest() throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var result = this.departmentRepository.findDepartments(DepartmentCriteria.builder().name("test").build()).get(0);
        result.setName("changed-dept");

        var json = mapper.writeValueAsString(DepartmentInput.convert(result));
        var changeResponse = this.graphQLTestTemplate.postMultipart("mutation SaveDepartment($department: DepartmentInput!){ saveDepartment(department: $department) {name} }", "{\"department\":" + json + "}");
        this.entityManager.clear();

        var changed = this.departmentRepository.findById(result.getDeptId());
        Assertions.assertTrue(changed.isPresent() && changed.get().getName().equals("changed-dept"));
    }
}
