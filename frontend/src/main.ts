import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { TaskListComponent } from './app/components/task-list/task-list.component';
import { TaskFormComponent } from './app/components/task-form/task-form.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    provideRouter([
      {path: '', component: TaskListComponent},
      {path: 'create', component: TaskFormComponent},
      {path: 'edit/:id', component: TaskFormComponent}
    ])
  ]
})
  .catch(err => console.error(err));
