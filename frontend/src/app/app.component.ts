import {Component, OnInit} from '@angular/core';
import {Employee} from "./employee";
import {EmployeeService} from "./employee.service";
import {HttpErrorResponse} from "@angular/common/http";
import {catchError, tap, throwError} from "rxjs";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'employeemanager';
  employees: Employee[];
  editEmployee: Employee;
  deleteEmployee: Employee;

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit() {
    this.getEmployees();
  }

  public getEmployees(): void {
    this.employeeService.getEmployees()
      .pipe(
        tap((response: Employee[]) => {
          this.employees = response;
        }),
        catchError((error: HttpErrorResponse) => {
          alert(error.message);
          return throwError(() => error);
        })
      )
      .subscribe();
  }

  public onAddEmployee(addForm: NgForm) {
    let closeButton = document.getElementById("add-employee-form");
    if (!closeButton) {
      console.error('Element with id "add-employee-form" not found in the DOM.');
      return;
    }
    closeButton.click();
    this.employeeService.addEmployee(addForm.value).pipe(
      tap((response: Employee) => {
        console.log(response);
        this.getEmployees();
        addForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        return throwError(() => error);
      })
    ).subscribe();
  }

  public onEditEmployee(employee: Employee) {
    let closeButton = document.getElementById("edit-employee-close");
    if (!closeButton) {
      console.error('Element with id "add-employee-form" not found in the DOM.');
      return;
    }
    closeButton.click();

    this.employeeService.updateEmployee(employee).pipe(
      tap((response: Employee) => {
        console.log(response);
        this.getEmployees();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public onDeleteEmployee(employeeId: number) {
    this.employeeService.deleteEmployee(employeeId).pipe(
      tap((response: string) => {
        console.log(response);
        this.getEmployees();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public searchEmployees(key: string) {
    const results: Employee[] = [];

    for (const employee of this.employees) {
      if (employee.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || employee.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || employee.phoneNumber.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || employee.jobTitle.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(employee);
      }
    }
    this.employees = results;
    if(results.length === 0 || !key){
      this.getEmployees();
    }
  }

  public onOpenModal(mode: string, employee?: Employee): void {
    const container = document.getElementById('main-container')

    if (!container) {
      console.error('Element with id "main-container" not found in the DOM.');
      return;
    }

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute("data-toggle", "modal");

    if (mode === 'add') {
      button.setAttribute("data-target", "#addEmployeeModal");
    }
    if (mode === 'edit') {
      if (employee) {
        this.editEmployee = employee;
      }
      button.setAttribute("data-target", "#updateEmployeeModal");
    }
    if (mode === 'delete') {
      if (employee) {
        this.deleteEmployee = employee;
      }
      button.setAttribute("data-target", "#deleteEmployeeModal");
    }
    container.appendChild(button);
    button.click();
  }
}
