import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Task, TaskStatus } from '../../models/task.model';
import { TaskService } from '../../services/task.service';
import { TaskStore } from '../../store/task.store';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent {

  successMessage: string | null = null;
  taskId: number | null = null;

  task: Task = {
    title: '',
    description: '',
    status: TaskStatus.TO_DO,
    createdAt: new Date().toISOString().split('T')[0],
    dueAt: new Date().toString().split('T')[0],
  }
  
  constructor(
    private taskService: TaskService,
    private taskStore: TaskStore,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  onSubmit() {
    this.taskService.createTask(this.task).subscribe({
      next: (created) => {
        console.log("La tâche a été ajoutée", created);
        this.taskStore.addTask(created);

        // Réinitialiser le formulaire
        this.task = {
          title: '',
          description: '',
          status: TaskStatus.TO_DO,
          dueAt: new Date().toISOString().split('T')[0],
        };

        // Afficher le message de succès
        this.successMessage = 'Tâche créée avec succès 🎉';

        // Effacer le message après 3 secondes
        setTimeout(() => {
          this.successMessage = null;
        }, 3000)
      },
      error: (err) => {
        console.error('Erreur lors de la création de la tâche :', err);
      }
    });
  }

}
