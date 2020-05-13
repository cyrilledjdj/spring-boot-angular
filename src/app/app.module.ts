import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppService } from './app.service';
import { ResultsComponent } from './results/results.component';

@Injectable({
	providedIn: 'root'
})
export class XhrInterceptor implements HttpInterceptor {
	intercept(req: HttpRequest<any>, next: HttpHandler) {
		const xhr = req.clone({
			headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
		});
		return next.handle(xhr);
	}
}

@NgModule({
	declarations: [ AppComponent, ResultsComponent ],
	imports: [
		CommonModule,
		BrowserModule,
		HttpClientModule,
		BrowserAnimationsModule,
		FormsModule,
		ReactiveFormsModule,
		AppRoutingModule,
		MatButtonModule,
		MatInputModule,
		MatFormFieldModule,
		MatIconModule,
		MatPaginatorModule,
		MatTableModule,
		MatProgressSpinnerModule,
		MatCardModule
	],
	providers: [ MatIconRegistry, AppService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true } ],
	bootstrap: [ AppComponent ]
})
export class AppModule {}
