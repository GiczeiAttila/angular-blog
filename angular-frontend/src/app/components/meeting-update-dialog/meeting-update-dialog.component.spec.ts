import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MeetingUpdateDialogComponent} from './meeting-update-dialog.component';

describe('MeetingUpdateDialogComponent', () => {
    let component: MeetingUpdateDialogComponent;
    let fixture: ComponentFixture<MeetingUpdateDialogComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MeetingUpdateDialogComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MeetingUpdateDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
