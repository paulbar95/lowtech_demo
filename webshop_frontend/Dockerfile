# Stage 1: Build Vue.js app
FROM node:22-alpine AS builder

# Set work directory
WORKDIR /app

# Copy package.json and install dependencies using npm
COPY package.json package-lock.json ./
RUN npm install  

# Copy source code
COPY . .

# API Base URL 
ARG API_BASE_URL
ENV VITE_API_BASE_URL=${API_BASE_URL}
ENV VUE_APP_API_BASE_URL=${API_BASE_URL}

RUN npm run build 

# Stage 2: Serve with Nginx
FROM nginx:alpine

# Copy built files from builder stage
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
