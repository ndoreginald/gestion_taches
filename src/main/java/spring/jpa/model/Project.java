package spring.jpa.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<Task>();;
//@OneToMany (mappedBy = "project" ,cascade = {CascadeType.REMOVE,
 //   CascadeType.MERGE, CascadeType.PERSIST} )
   // private Collection <Task> tasks = new ArrayList<Task>();
    public Long getId() {
        return id;
    }

    public Project(String nom, List<Task> tasks) {
		super();
		this.nom = nom;
		this.tasks = tasks;
	}
    

	public Project(String nom) {
		super();
		this.nom = nom;
	}

	public Project() {
		super();
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

	@Override
	public String toString() {
		return "Project [id=" + id + ", nom=" + nom + ", tasks=" + tasks + "]";
	}
   
}
