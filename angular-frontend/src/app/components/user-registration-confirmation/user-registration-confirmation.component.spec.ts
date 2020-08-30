import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRegistrationConfirmationComponent } from './user-registration-confirmation.component';

describe('UserRegistrationConfirmationComponent', () => {
  let component: UserRegistrationConfirmationComponent;
  let fixture: ComponentFixture<UserRegistrationConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserRegistrationConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserRegistrationConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
