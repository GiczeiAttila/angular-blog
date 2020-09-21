import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MeetingDialodComponent} from './meeting-dialod.component';

describe('MeetingDialodComponent', () => {
    let component: MeetingDialodComponent;
    let fixture: ComponentFixture<MeetingDialodComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MeetingDialodComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MeetingDialodComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
