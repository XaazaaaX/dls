interface Window {
  env: {
    apiUrl: string;
    debug: boolean;
    [key: string]: any; // Optional: allow other env properties
  };
}