export interface Alerta {
  id: number;
  studentId: number;
  studentName: string;
  studentLastName: string;
  type: string;
  severity: string;
  status: string;
  description: string;
  metricValue?: number;
  generatedDate?: string;
  period?: string;
}
