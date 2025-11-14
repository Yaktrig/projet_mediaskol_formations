import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormationSupprimerModal } from './formation-supprimer-modal';

describe('FormationSupprimerModal', () => {
  let component: FormationSupprimerModal;
  let fixture: ComponentFixture<FormationSupprimerModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormationSupprimerModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormationSupprimerModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
