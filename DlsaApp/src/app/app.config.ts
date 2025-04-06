import { ApplicationConfig, provideZoneChangeDetection, importProvidersFrom } from '@angular/core';
import { provideRouter, withRouterConfig } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import { registerLocaleData } from '@angular/common';
import de from '@angular/common/locales/de';
import { FormsModule } from '@angular/forms';
import Noir from './../theme';

registerLocaleData(de);

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withRouterConfig({ onSameUrlNavigation: 'reload' })),
    provideAnimationsAsync(),
    providePrimeNG({ theme: { preset: Noir, options: { darkModeSelector: false } } }),
    importProvidersFrom(FormsModule), 
    provideAnimationsAsync(), 
    provideHttpClient()
  ]
};