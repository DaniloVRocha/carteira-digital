import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizarContasComponent } from './visualizar-contas.component';

describe('VisualizarContasComponent', () => {
  let component: VisualizarContasComponent;
  let fixture: ComponentFixture<VisualizarContasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisualizarContasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizarContasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
