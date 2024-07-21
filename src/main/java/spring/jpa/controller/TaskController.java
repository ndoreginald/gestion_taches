package spring.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import spring.jpa.model.Project;
import spring.jpa.model.Task;
import spring.jpa.repository.ProjetRepository;
import spring.jpa.repository.TaskRepository;

@Controller
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepos;
    
    @Autowired
    private ProjetRepository projetRepos;

	/*
	 * @GetMapping public String getAllTasks(Model model) { List<Task> tasks =
	 * taskRepos.findAll(); model.addAttribute("tasks", tasks); return "task-list";
	 * }
	 */

	/*
	 * @GetMapping("/new") public String showTaskForm(@PathVariable("projectId")
	 * Long projectId, Project project, Model model) { Task task = new Task();
	 * task.setProject(project); model.addAttribute("task", task); return
	 * "task-form"; }
	 */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id: " + id));
        return ResponseEntity.ok(task);
    }
    
	/*
	 * @GetMapping("/new") public String showTaskForm(@PathVariable("projectId")
	 * Long projectId, Model model) { Project project =
	 * projetRepos.findById(projectId) .orElseThrow(() -> new
	 * IllegalArgumentException("Invalid project Id:" + projectId)); Task task = new
	 * Task(); task.setProject(project); model.addAttribute("task", task);
	 * model.addAttribute("projectId", projectId); return "task-form"; }
	 */

	/*
	 * @PostMapping public String saveTask(@PathVariable("projectId") Long
	 * projectId, Project project, @Valid @ModelAttribute("task") Task task,
	 * BindingResult result) { if (result.hasErrors()) { return "task-form"; }
	 * task.setProject(project); // Assigner le projet ID au cas où il ne serait pas
	 * déjà défini taskRepos.save(task); return "redirect:/projects/" + projectId +
	 * "/tasks"; }
	 */

	/*
	 * @GetMapping("/edit/{taskId}") public String editTaskForm(@PathVariable Long
	 * taskId, Model model) { Task task = taskRepos.findById(taskId).orElseThrow(()
	 * -> new IllegalArgumentException("Invalid task Id:" + taskId));
	 * model.addAttribute("task", task); return "task-form"; }
	 * 
	 * @PostMapping("/{taskId}") public String updateTask(@PathVariable Long
	 * projectId,Project project, @PathVariable Long taskId, @ModelAttribute Task
	 * task) { task.setId(taskId); task.setProject(project); // Assurez-vous que la
	 * tâche est associée au bon projet taskRepos.save(task); return
	 * "redirect:/projects/" + projectId + "/tasks"; }
	 */



    
}
