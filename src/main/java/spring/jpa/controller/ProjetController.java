package spring.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import spring.jpa.model.Project;
import spring.jpa.model.Task;
import spring.jpa.repository.ProjetRepository;
import spring.jpa.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/projects")
public class ProjetController {

    @Autowired
    private ProjetRepository projetRepos;
    
    @Autowired
    private TaskRepository taskRepos;

    @GetMapping
    public String getAllProjects(Model model) {
        List<Project> projects = projetRepos.findAll();
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/new")
    public String createProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "project-form";
    }

    @PostMapping
    public String saveProject(@ModelAttribute Project project) {
    	projetRepos.save(project);
        return "redirect:/projects";
    }

    @GetMapping("/edit/{id}")
    public String editProjectForm(@PathVariable Long id, Model model,Project project) {
         project = projetRepos.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
        model.addAttribute("project", project);
        return "editProject-form";
    }

    @PostMapping("/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute Project project) {
        project.setId(id);
        projetRepos.save(project);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
    	projetRepos.deleteById(id);
        return "redirect:/projects";
    }
    
    
    //partie controller coteé taches
    
    
    @GetMapping("/{id}/tasks")
    public String viewProjectTasks(@PathVariable("id") Long id, Model model) {
        Optional<Project> project = projetRepos.findById(id);
        if (project.isPresent()) {
            List<Task> tasks = taskRepos.findByProjectId(id);
            model.addAttribute("project", project.get());
            model.addAttribute("tasks", tasks);
            return "task-list";
        } else {
            // Gérer le cas où le projet n'est pas trouvé
            return "errorPage"; // Assurez-vous d'avoir une vue d'erreur appropriée
        }
    }

    @GetMapping("/{id}/tasks/new")
    public String showTaskForm(@PathVariable("id") Long id, Model model, Project project,Task task) {
         project = projetRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
         task = new Task();
        task.setProject(project);
        model.addAttribute("task", task);
        model.addAttribute("project", project);  // Ajoutez l'objet project au modèle
        return "task-form";
    }


    @PostMapping("/{id}/tasks/saveTask")
    public String saveTask(@PathVariable("id") Long id , Project project, @Valid Task task, BindingResult result, Model model) {
    	 if (result.hasErrors()) {
    	        model.addAttribute("project", project);
    	        model.addAttribute("task", task);
    	        return "task-form";
    	    }
    	project = projetRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
    	task.setProject(project);
    	
    	taskRepos.save(task);
        return "redirect:/projects/" + id + "/tasks";  // Redirige vers la liste des tâches du projet
    }
    
 // Méthode pour afficher le formulaire de modification d'une tâche
    @GetMapping("/{projectId}/tasks/edit/{taskId}")
    public String editTaskForm(@PathVariable Long projectId, @PathVariable Long taskId, Model model,Project project,Task task) {
         project = projetRepos.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + projectId));
         task = taskRepos.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));
        
        model.addAttribute("project", project);
        model.addAttribute("task", task);
        return "editTask-form";
    }

    // Méthode pour mettre à jour une tâche existante
    @PostMapping("/{projectId}/tasks/updateTask/{taskId}")
    public String updateTask(@PathVariable Long projectId, @PathVariable Long taskId, 
                             @Valid Task task, BindingResult result, Model model,Project project) {
    	if (result.hasErrors()) {
            model.addAttribute("projectId", projectId);
            return "editTask-form";
        }
         project = projetRepos.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + projectId));

        task.setId(taskId);
        task.setProject(project);
        taskRepos.save(task);
        return "redirect:/projects/" + projectId + "/tasks";
    }
    
 // Méthode pour supprimer une tâche
    @GetMapping("/{projectId}/tasks/delete/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId,Task task) {
         task = taskRepos.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));
        
        taskRepos.delete(task);
        return "redirect:/projects/" + projectId + "/tasks";
    }



    
}

