FROM postgres:11.2-alpine

HEALTHCHECK --interval=10s --timeout=10s --retries=10 CMD psql -c 'select 1' -d javadatabase -U javapostgres

EXPOSE 5432
