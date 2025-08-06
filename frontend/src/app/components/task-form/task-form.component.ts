import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Task, TaskStatus } from '../../models/task.model';
import { TaskService } from '../../services/task.service';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent {
  task: Task = {
    title: '',
    description: '',
    status: TaskStatus.TO_DO,
    createdAt: new Date().toISOString().split('T')[0],
    dueAt: new Date().toString().split('T')[0],
  }
  
  constructor(private taskService: TaskService) {}

  onSubmit() {
    this.taskService.createTask(this.task).subscribe({
      next: (created) => {
        console.log("La tâche a été ajoutée", created);
        // TODO : rediriger ou réinitialiser le formulaire
      },
      error: (err) => {
        console.error('Erreur lors de la création de la tâche :', err);
      }
    });
  }

}
