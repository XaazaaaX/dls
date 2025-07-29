export const environment = {
  production: false,
  apiUrl: (window as any).__env?.API_URL || 'http://127.0.0.1:5005'
};