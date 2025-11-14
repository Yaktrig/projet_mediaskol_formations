import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormationAjouter } from './formation-ajouter';

describe('FormationAjouter', () => {
  let component: FormationAjouter;
  let fixture: ComponentFixture<FormationAjouter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormationAjouter]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormationAjouter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
