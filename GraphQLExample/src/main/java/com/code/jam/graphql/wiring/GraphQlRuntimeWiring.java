package com.code.jam.graphql.wiring;

import com.code.jam.graphql.data.InMemoryData;
import com.code.jam.graphql.protocol.Employee;
import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.stereotype.Component;

@Component
public class GraphQlRuntimeWiring {

    private static final String EMPLOYEE_ID_ARGUMENT = "EmployeeId";

    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("employee", getEmployee))
                .type(TypeRuntimeWiring.newTypeWiring("Employee")
                        .dataFetcher("tasks", getEmployeeTask))
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("employees", getEmployees))
                .build();
    }

    final DataFetcher getEmployees = environment -> InMemoryData.getAllEmployees();

    final DataFetcher getEmployee = environment -> {
        final int employeeId = Integer.parseInt(environment.getArgument(EMPLOYEE_ID_ARGUMENT));
        return InMemoryData.getEmployeeById(employeeId);
    };

    final DataFetcher getEmployeeTask = environment -> {
        final Employee employee = environment.getSource();
        return InMemoryData.getTasksByEmployeeId(employee.getId());
    };
}
