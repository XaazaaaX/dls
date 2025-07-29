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

# When the container starts, replace the env.js with values from environment variables
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/env.template.js > /usr/share/nginx/html/assets/env.js && exec nginx -g 'daemon off;'"]