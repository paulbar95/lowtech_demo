FROM postgres:14.15

# Set environment variables for PostgreSQL
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=root
ENV POSTGRES_DB=db_lowtech

# Copy initial schema setup script
COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 5432
