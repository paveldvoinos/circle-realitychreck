CREATE TABLE service_template_example (
  id               SERIAL      PRIMARY KEY,
  colum_bigint     BIGINT      NOT NULL,
  column_varchar   VARCHAR(64) NOT NULL,
  column_date      DATE        NOT NULL
);