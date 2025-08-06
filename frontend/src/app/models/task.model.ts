export enum taskStatus {
    TO_DO = 'TO_DO',
    IN_PROGRESS = 'IN_PROGRESS',
    DONE = 'DONE'
}

export interface Task {
    id?: number;
    title: string;
    description: string;
    status: taskStatus;
    createdAt: String;
    dueAt: String;
}