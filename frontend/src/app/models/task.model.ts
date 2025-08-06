export enum TaskStatus {
  TO_DO = 'À faire',
  IN_PROGRESS = 'En cours',
  COMPLETED = 'Terminé'
}

export interface Task {
  id?: number;
  title: string;
  description: string;
  status: TaskStatus;
  createdAt?: string;
  dueAt: string;
}
