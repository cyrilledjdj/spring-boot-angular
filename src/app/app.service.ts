import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PeriodicElement } from './results/results.component';

@Injectable({ providedIn: 'root' })
export class AppService {
	constructor(private readonly httpClient: HttpClient) {}

	numberChecker(number) {
		return this.httpClient.post('tel', number);
	}

	getPhoneNumber(number: string, index: number, size: number) {
		return this.httpClient.get<PeriodicElement[]>(`tel/${number}/${size}/${index}`);
	}
}
