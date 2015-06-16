-- Creation of metadata tables
DROP TABLE hec_catalogue;
CREATE TABLE hec_catalogue (
        cat_id                  INTEGER,
        name                    VARCHAR(32),
        description             VARCHAR(128),
        type                    VARCHAR(20),
        status                  VARCHAR(20),
        url                     VARCHAR(128),
        hec_groups_id           INTEGER,
        bg_color                VARCHAR(15),
        longdescription         VARCHAR(256),
        timefrom   DATE,
        timeto     DATE,
        update_timeto  VARCHAR(2),
        flare  VARCHAR(2),
        cme    VARCHAR(2),
        swind  VARCHAR(2),
        part   VARCHAR(2),
        otyp   VARCHAR(2),
        solar  VARCHAR(2),
        ips    VARCHAR(2),
        geo    VARCHAR(2),
        planet    VARCHAR(2),
        sort INTEGER,
        PRIMARY KEY (cat_id)    
);

GRANT SELECT ON TABLE hec_catalogue TO apache;
