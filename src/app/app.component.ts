import { Component, OnInit, Input } from '@angular/core';
import { AbstractControl, FormControl, FormGroupDirective, NgForm, Validator, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { parsePhoneNumberFromString, PhoneNumber } from 'libphonenumber-js';
import { AppService } from './app.service';
import { PageEvent } from '@angular/material/paginator';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
	isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
		const isSubmitted = form && form.submitted;
		return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
	}
}

/** @title Input with a custom ErrorStateMatcher */
@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: [ './app.component.scss' ]
})
export class AppComponent implements OnInit, Validator {
	telephoneControl: FormControl = new FormControl('', [ Validators.required, this.validate ]);
	matcher = new MyErrorStateMatcher();
	phoneNumber: PhoneNumber;
	showResults: boolean = false;
	count: number;

	constructor(private readonly appService: AppService) {}

	ngOnInit(): void {
		//Called after the constructor, initializing input properties, and the first call to ngOnChanges.
		//Add 'implements OnInit' to the class.
		this.telephoneControl.valueChanges.subscribe((val) => {
			this.phoneNumber = parsePhoneNumberFromString(val, 'US');
		});
	}

	validate(control: AbstractControl) {
		const phoneNumber = parsePhoneNumberFromString(control && control.value, 'US');
		if (phoneNumber && !phoneNumber.isValid()) {
			return { telephone: true };
		}
		return null;
	}

	public submit() {
		if (this.telephoneControl.valid) {
			this.showResults = false;
			this.appService
				.numberChecker(this.telephoneControl.value)
				.subscribe((data: { valid: boolean; validAlphaNumericAccount: number }) => {
					this.showResults = data.valid;
					this.count = data.validAlphaNumericAccount;
				});
		}
	}

	getResult(data: PageEvent) {
		//TODO do something with current page
	}
}
