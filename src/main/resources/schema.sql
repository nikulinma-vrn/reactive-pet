DROP TABLE IF EXISTS owners CASCADE;
DROP TABLE IF EXISTS pets CASCADE;
DROP TABLE IF EXISTS types CASCADE;
DROP TABLE IF EXISTS specialties CASCADE;
DROP TABLE IF EXISTS vets CASCADE;
DROP TABLE IF EXISTS visits CASCADE;
DROP TABLE IF EXISTS specialties_vets CASCADE;



CREATE TABLE IF NOT EXISTS owners (
    id BIGSERIAL NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(255),
    telephone VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS types (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS pets (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    birth_date DATE,
    type_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (type_id) REFERENCES types ON DELETE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES owners ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS specialties (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vets (
    id BIGSERIAL NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS visits (
    id BIGSERIAL NOT NULL,
    visit_date DATE,
    description VARCHAR(255),
    pet_id BIGINT NOT NULL,
    assigned_vet_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (pet_id) REFERENCES pets ON DELETE CASCADE,
    FOREIGN KEY (assigned_vet_id) REFERENCES vets ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS specialties_vets (
    id BIGSERIAL NOT NULL,
    specialties_id BIGINT NOT NULL,
    vets_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (specialties_id) REFERENCES specialties ON DELETE CASCADE,
    FOREIGN KEY (vets_id) REFERENCES vets ON DELETE CASCADE,
    UNIQUE (specialties_id, vets_id)
);