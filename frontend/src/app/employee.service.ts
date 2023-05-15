import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Employee} from "./employee";
import {environment} from "../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiServerUrl = environment.apiBaseUrl;
  constructor(private http: HttpClient) { }

  public getEmployees(): Observable<Employee[]>{
    return this.http.get<Employee[]>(`${this.apiServerUrl}/api/v1/employeemanager/employee/all`);
  }

  public addEmployee(employee: Employee): Observable<Employee>{
    return this.http.post<Employee>(`${this.apiServerUrl}/api/v1/employeemanager/employee/add`, employee);
  }

  public addAllEmployees(employees: Employee[]): Observable<Employee[]>{
    return this.http.post<Employee[]>(`${this.apiServerUrl}/api/v1/employeemanager/employee/addAll`, employees);
  }

  public updateEmployee(employee: Employee): Observable<Employee>{
    return this.http.put<Employee>(`${this.apiServerUrl}/api/v1/employeemanager/employee/update`, employee);
  }

  public deleteEmployee(employeeId: number): Observable<string>{
    return this.http.delete<string>(`${this.apiServerUrl}/api/v1/employeemanager/employee/delete/${employeeId}`);
  }



}
