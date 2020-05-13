import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { PhoneNumber } from 'libphonenumber-js';
import { AppService } from '../app.service';

@Component({
	selector: 'app-results',
	templateUrl: './results.component.html',
	styleUrls: [ './results.component.scss' ]
})
export class ResultsComponent implements OnInit {
	@Output() more = new EventEmitter<PageEvent>();
	@Input() results: number;
	@Input() number: PhoneNumber;
	loading = false;

	paginatorChange(data: PageEvent) {
		this.more.emit(data);
		this.loading = true;
		this.appService
			.getPhoneNumber(this.number.nationalNumber as string, data.pageIndex, data.pageSize + 1)
			.subscribe((data) => {
				this.dataSource = new MatTableDataSource<PeriodicElement>(data);
				this.loading = false;
			});
	}

	displayedColumns: string[] = [ 'number' ];
	dataSource: MatTableDataSource<PeriodicElement>;

	constructor(private readonly appService: AppService) {}

	ngOnInit() {
		this.loading = true;
		this.appService.getPhoneNumber(this.number.nationalNumber as string, 0, 11).subscribe((data) => {
			this.dataSource = new MatTableDataSource<PeriodicElement>(data);
			this.loading = false;
		});
	}
}

export interface PeriodicElement {
	alpha: string;
}
