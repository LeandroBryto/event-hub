export interface ApiError {
    status: number;
    message: string;
    errors?: FieldError[];
}

export interface FieldError {
    field: string;
    message: string;
}
