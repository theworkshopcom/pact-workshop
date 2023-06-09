import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  {
    path:'',
    component: HomeComponent,
    pathMatch:'prefix',
    children: [
      {
        path: '',
        loadChildren:()=>import('productAngularMicrofrontend/Module').then(m=>m.ProductModule),
        outlet: 'product'
      },
      {
        path: '',
        loadChildren:()=>import('cartAngularMicrofrontend/Module').then(m=>m.CartModule),
        outlet: 'cart'
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
