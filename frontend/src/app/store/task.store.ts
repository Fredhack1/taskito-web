import { Injectable, signal, computed } from "@angular/core";
import { Task } from "../models/task.model";


@Injectable({ providedIn: 'root' })
export class TaskStore {
    private readonly _tasks = signal<Task[]>([]);

    readonly tasks = computed(() => this._tasks());

    setTasks(tasks: Task[]) {
        this._tasks.set(tasks);
    }

    addTask(task: Task) {
        this._tasks.update((prev) => [task, ...prev]);
    }

    removeTask(id: number) {
        this._tasks.update((prev) => prev.filter(t => t.id !== id));
    }

    clear() {
        this._tasks.set([]);
    }
}