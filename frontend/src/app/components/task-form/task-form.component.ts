import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Task, TaskStatus } from '../../models/task.model';
import { TaskService } from '../../services/task.service';
import { TaskStore } from '../../store/task.store';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent implements OnInit {

  form!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
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
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      status: ['', Validators.required],
      dueAt: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      console.log("tâche soumise :", this.form.value);

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
    } else {
      // Afficher le message d'erreur
      this.errorMessage = "Formulaire invalide ! ❌";

      // Effacer le message après 5 secondes
      setTimeout(() => {
        this.errorMessage = null;
      }, 5000);
    }
  }

}
