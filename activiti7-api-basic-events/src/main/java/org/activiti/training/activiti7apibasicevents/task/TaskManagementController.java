package org.activiti.training.activiti7apibasicevents.task;

import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskManagementController {
    private Logger logger = LoggerFactory.getLogger(TaskManagementController.class);

    @Autowired
    private TaskRuntime taskRuntime;

    @GetMapping("/my-tasks")
    public List<Task> getMyTasks() {
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        logger.info("My available tasks: " + tasks.getTotalItems());

        for(Task task: tasks.getContent()) {
            logger.info("> My User Task: " + task);
        }

        return tasks.getContent();
    }


    @RequestMapping("/complete-task")
    public String completeTask(@RequestParam(value = "taskId") String taskId) {
        taskRuntime.complete(TaskPayloadBuilder
                .complete()
                .withTaskId(taskId)
                .build()
        );
        logger.info(">>> Completed task: " + taskId);

        return ">>> Completed task: " + taskId;
    }

    @Autowired
    private TaskAdminRuntime taskAdminRuntime;

    @GetMapping("/all-tasks")
    public List<Task> getAllTasks() {
        Page<Task> tasks = taskAdminRuntime.tasks(Pageable.of(0,10));
        logger.info("All available tasks: " + tasks.getTotalItems());

        for (Task task: tasks.getContent()) {
            logger.info("User task from Admin: " + task);
        }

        return tasks.getContent();
    }
}
