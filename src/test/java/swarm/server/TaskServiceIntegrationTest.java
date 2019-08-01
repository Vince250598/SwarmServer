package swarm.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import swarm.server.domains.Product;
import swarm.server.domains.Task;
import swarm.server.repositories.TaskRepository;
import swarm.server.services.TaskService;

public class TaskServiceIntegrationTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void whenTasksByProductId_thenReturnTasks() {

        Product product = new Product("product");

        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task(product, "title", "url", false));
        taskList.add(new Task(product, "title2", "url2", false));

        when(taskRepository.findByProductId(1L)).thenReturn(taskList);

        Iterable<Task> found = taskService.taskByProductId(1L);

        int i = 0;
        for (Task task : found) {
            assertEquals(taskList.get(i), task);
            i++;
        }
    }

    @Test
    public void whenTaskUpdateTitle_thenReturnTask() {

        Optional<Task> task;
        Task taskk = new Task();
        taskk.setId(1L);
        taskk.setTitle("title");
        task = Optional.of(taskk);

        when(taskRepository.findById(1L)).thenReturn(task);
        when(taskRepository.save(task.get())).thenReturn(task.get());

        Task saved = taskService.taskUpdateTitle(1L, "title2");

        assertEquals(taskk, saved);
        assert saved.getTitle() == "title2";
    }

    @Test
    public void whenTaskCreate_thenReturnTask() {

        Product product = new Product("yo");

        Task task = new Task(product, "123", "456", false);

        when(taskRepository.save(task)).thenReturn(task);

        Task saved = taskService.taskCreate(task);

        assertEquals(task, saved);
    }

    @Test
    public void whenTaskDone_thenReturnTask() {

        Product product = new Product("yo");

        Task task = new Task(product, "title", "url", false);
        task.setId(1L);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task updated = taskService.taskDone(task.getId());

        assertEquals(task, updated);
        assert task.getDone() == true;
    }

    @Test
    public void whenTasksActive_thenReturnTasks() {

        Product product = new Product("yo");

        Task task = new Task(product, "title", "url", false);
        task.setId(1L);

        List<Task> taskList = new ArrayList<Task>();
        taskList.add(task);

        when(taskRepository.findActiveTasksByDeveloperId(1L)).thenReturn(taskList);

        List<Task> found = taskService.activeTasks(1L, null);

        assert found.get(0) == taskList.get(0);
        assert found.get(0).getDone() == false;
    }
}