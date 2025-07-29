#!/bin/sh

echo "Using API_URL from environment variable: ${API_URL}"

cat <<EOF > /usr/share/nginx/html/assets/env.js
window.__env = {
  API_URL: "${API_URL}"
};
EOF

exec nginx -g 'daemon off;'