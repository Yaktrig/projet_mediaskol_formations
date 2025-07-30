import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeSessionFormationPresentiel } from './liste-session-formation-presentiel';

describe('ListeSessionFormationPresentiel', () => {
  let component: ListeSessionFormationPresentiel;
  let fixture: ComponentFixture<ListeSessionFormationPresentiel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListeSessionFormationPresentiel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListeSessionFormationPresentiel);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
