# build environment
FROM node:18-alpine AS builder
WORKDIR /app

COPY package.json .
COPY package-lock.json .
COPY postcss.config.cjs .
COPY vite.config.ts .
COPY .browserslistrc .
COPY tsconfig.json .
COPY tsconfig.node.json .
COPY .eslintrc.cjs .
COPY .env.production .

RUN npm i

COPY index.html .
COPY public public
COPY src src

RUN npm run build

# production environment
FROM nginx:1.21.0-alpine AS production
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
