package com.code.jam.graphql.data;

import com.code.jam.graphql.protocol.Employee;
import com.code.jam.graphql.protocol.Task;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Component
public class InMemoryData {

    private static Map<Integer, Employee> employeeMap;
    private static Map<Integer, Task> taskMap;

    static {
        employeeMap = new HashMap<>();
        employeeMap.put(101, new Employee(101, "Alex", "Oakland", "California", "US"));
        employeeMap.put(102, new Employee(102, "John", "San Francisco", "California", "US"));
        employeeMap.put(103, new Employee(103, "Green", "Seattle", "Washington", "US"));

        taskMap = new HashMap<>();
        taskMap.put(2001, new Task(2001, "Set up office" , 101, "Office Description", "Pending"));
        taskMap.put(2002, new Task(2002, "Clean desk" , 102, "Clean desk weekly", "Completed"));
        taskMap.put(2003, new Task(2003, "Call Mike" , 101, "Call to check quotation", "Pending"));
        taskMap.put(2004, new Task(2004, "Weekly Update" , 103, "Weekly update meeting", "Completed"));
    }

    public static CompletionStage<Employee> getEmployeeById(final int employeeId) {
        return CompletableFuture.supplyAsync(() -> employeeMap.get(employeeId));
    }

    public static CompletionStage<List<Employee>> getAllEmployees() {
        return CompletableFuture.supplyAsync(() -> employeeMap.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }

    public static CompletionStage<List<Task>> getTasksByEmployeeId(final int employeeId) {
        return CompletableFuture.supplyAsync(() -> taskMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getEmployeeId() == employeeId)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }
}
