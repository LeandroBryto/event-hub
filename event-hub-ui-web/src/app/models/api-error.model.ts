export interface FieldError {
  name: string;
  message: string;
}

export interface ApiError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  fields?: FieldError[];
}
