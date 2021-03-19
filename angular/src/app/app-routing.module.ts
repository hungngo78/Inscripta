import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { NavComponent } from './nav/nav.component';
import { EditRequestComponent } from './edit-request/edit-request.component';


export const routes: Routes = [
  {path: '', redirectTo: "/home", pathMatch: 'full' },
  {path: 'home', component: HomeComponent},
  {path: 'edit-request', component: EditRequestComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
