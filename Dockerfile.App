# Stage 1 – Angular Build
FROM node:18 AS build
WORKDIR /app
COPY . .
RUN npm ci
RUN npm run build -- --configuration production

# Stage 2 – Nginx
FROM nginx:alpine
COPY --from=build /app/dist/dlsa-app/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf