# 1. Angular-App bauen
FROM node:18 AS build
WORKDIR /app
COPY . .
RUN npm ci
RUN npm run build -- --configuration production

# 2. Build-Ergebnis mit Nginx bereitstellen
FROM nginx:alpine
COPY --from=build /app/dist/dlsaApp /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf