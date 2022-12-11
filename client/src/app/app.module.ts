import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReactiveFormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {MaterialModule} from './material/material.module';
import {HeaderComponent} from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { MainComponent } from './components/main/main.component';
import { SignInComponent } from './components/auth/sign-in/sign-in.component';
import { AuthComponent } from './components/auth/auth.component';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';
import { AdminComponent } from './components/admin/admin.component';
import { FacultiesComponent } from './components/main/faculties/faculties.component';
import { FacultyItemComponent } from './components/main/faculties/faculty-item/faculty-item.component';
import { FacultyComponent } from './components/main/faculties/faculty/faculty.component';
import { FacultyEditComponent } from './components/main/faculties/faculty/faculty-edit/faculty-edit.component';
import { DepartmentItemComponent } from './components/main/faculties/faculty/department-item/department-item.component';
import { PageTitleComponent } from './components/common/page-title/page-title.component';
import { InfoBlockComponent } from './components/common/info-block/info-block.component';
import { TopInfoBlockComponent } from './components/common/top-info-block/top-info-block.component';
import { DepartmentViewComponent } from './components/main/department/department-view/department-view.component';
import { LecturerItemComponent } from './components/main/department/lecturer-item/lecturer-item.component';
import {AuthGuard} from './guards/auth.guard';
import {AuthService} from './services/auth/auth.service';
import { AdminFacultiesComponent } from './components/admin/admin-faculties/admin-faculties.component';
import { AdminFacultyItemComponent } from './components/admin/admin-faculties/admin-faculty-item/admin-faculty-item.component';
import {ApolloClientModule} from './apollo/apollo.client.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainComponent,
    SignInComponent,
    AuthComponent,
    SignUpComponent,
    AdminComponent,
    FacultiesComponent,
    HeaderComponent,
    FacultyItemComponent,
    FacultyComponent,
    FacultyEditComponent,
    DepartmentItemComponent,
    PageTitleComponent,
    InfoBlockComponent,
    TopInfoBlockComponent,
    DepartmentViewComponent,
    LecturerItemComponent,
    AdminFacultiesComponent,
    AdminFacultyItemComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    ApolloClientModule,
    HttpClientModule,
    MaterialModule
  ],
  providers: [
    AuthService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
