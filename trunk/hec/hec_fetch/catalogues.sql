DROP TABLE catalogues;
CREATE TABLE catalogues (
        catalogues_id           SERIAL,
        table_name              VARCHAR(30),
        description             VARCHAR(128),
        type                    VARCHAR(20),
        status                  VARCHAR(20),
        time_from  TIMESTAMP,
        time_to    TIMESTAMP,
        PRIMARY KEY (catalogues_id)    
);
GRANT SELECT ON TABLE catalogues TO apache;

COPY catalogues (table_name,description,type,status,time_from,time_to) FROM '/var/www/hec/temp/catalogues.postgres.converted';
