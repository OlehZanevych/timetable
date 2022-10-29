import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './guards/auth.guard';
import {AdminComponent} from './components/admin/admin.component';
import {MainComponent} from './components/main/main.component';
import {AuthComponent} from './components/auth/auth.component';
import {SignInComponent} from './components/auth/sign-in/sign-in.component';
import {SignUpComponent} from './components/auth/sign-up/sign-up.component';
import {FacultiesComponent} from './components/main/faculties/faculties.component';
import {FacultyComponent} from './components/main/faculties/faculty/faculty.component';
import {FacultyEditComponent} from './components/main/faculties/faculty/faculty-edit/faculty-edit.component';
import {DepartmentViewComponent} from './components/main/department/department-view/department-view.component';

const DEPARTMENT_ROOT_ROUTE = 'departments';
const FACULTY_ROOT_ROUTE = 'faculties';

const routes: Routes = [{
  path: 'admin', component: AdminComponent, canActivate: [AuthGuard]
}, {
  path: 'auth', component: AuthComponent,
  children: [{
    path: 'sign-up', component: SignUpComponent
  }, {
    path: '**', component: SignInComponent
  }]
}, {path: `${FACULTY_ROOT_ROUTE}/:id`, component: FacultyComponent},
  {path: `${FACULTY_ROOT_ROUTE}/:id/edit`, component: FacultyEditComponent},
  {path: `${DEPARTMENT_ROOT_ROUTE}/:id`, component: DepartmentViewComponent},{
  path: '**', component: FacultiesComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
