import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormationModifierModal } from './formation-modifier-modal';

describe('FormationModifierModal', () => {
  let component: FormationModifierModal;
  let fixture: ComponentFixture<FormationModifierModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormationModifierModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormationModifierModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
