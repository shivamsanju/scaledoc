package com.shvmsnju.scaledoc_project_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shvmsnju.scaledoc_project_service.entity.Project;
import com.shvmsnju.scaledoc_project_service.exception.ResourceNotFoundException;
import com.shvmsnju.scaledoc_project_service.repository.ProjectRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<Project> getProjects() {
        return projectRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @PostMapping
    public Project createProject(@Valid @RequestBody Project project) {
        project.setCreatedBy("John Doe");
        Project newProject = projectRepository.save(project);
        return newProject;
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable UUID id) {
        var project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Project with id %s not found", id));
        }
        return project.get();
    }

    @DeleteMapping("/{id}")
    public void removProjectById(@PathVariable UUID id) {
        projectRepository.deleteById(id);
    }

}
