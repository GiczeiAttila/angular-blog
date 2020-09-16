import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MeetingReservationFormComponent} from './meeting-reservation-form.component';

describe('MeetingReservationFormComponent', () => {
    let component: MeetingReservationFormComponent;
    let fixture: ComponentFixture<MeetingReservationFormComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MeetingReservationFormComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MeetingReservationFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
