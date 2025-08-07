import { Component, OnInit } from '@angular/core';
import { TaskService } from '../../services/task.service';
import { Task } from '../../models/task.model';
import { CommonModule } from '@angular/common';
import { TaskStore } from '../../store/task.store';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private taskService: TaskService, public taskStore: TaskStore) {}

  deleteTask(id: number) {
  this.taskService.deleteTask(id).subscribe({
    next: () => {
      this.taskStore.removeTask(id);
    },
    error: (err) => {
      console.error('Erreur suppression tâche :', err);
    }
  });
}

  ngOnInit(): void {
    this.taskService.getAllTasks().subscribe((data) => {
      console.log("Tâches reçues :", data);
      this.taskStore.setTasks(data);
    });
  }

}
