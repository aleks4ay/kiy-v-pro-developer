CREATE TABLE client
(
    id VARCHAR(9) PRIMARY KEY NOT NULL,
    name VARCHAR
);

CREATE TABLE worker
(
    id VARCHAR(9) PRIMARY KEY NOT NULL,
    name VARCHAR
);

CREATE TABLE embodiment
(
    id VARCHAR(9) PRIMARY KEY NOT NULL,
    description VARCHAR
);

CREATE TABLE tmc
(
  id VARCHAR(9) PRIMARY KEY NOT NULL,
  id_parent VARCHAR(9),
  code VARCHAR(5),
  descr VARCHAR(50),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  is_folder INTEGER,
  descr_all VARCHAR(100),
  type VARCHAR(9),
  store_c INTEGER DEFAULT 0
);

/*CREATE TABLE techno_item
(
  id VARCHAR(9) PRIMARY KEY NOT NULL,
  id_parent VARCHAR(9),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  descr VARCHAR,
  store_c INTEGER DEFAULT 0
);*/

CREATE TABLE journal
(
    id VARCHAR(9) PRIMARY KEY NOT NULL,
    doc_number VARCHAR(10),
    t_create TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE orders
(
  id VARCHAR(9) PRIMARY KEY NOT NULL,
  id_client VARCHAR(9),
  id_manager VARCHAR(9),
  duration INTEGER,
  t_factory TIMESTAMP WITHOUT TIME ZONE,
  price NUMERIC(14,3)
);

CREATE TABLE descriptions
(
  id VARCHAR(13) PRIMARY KEY NOT NULL,
  id_order VARCHAR(9),
  position INTEGER,
  id_tmc VARCHAR(9),
  quantity INTEGER,
  descr_second VARCHAR(300),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  embodiment VARCHAR,
  FOREIGN KEY (id_order) REFERENCES orders (id) ON DELETE CASCADE
);

create sequence times_id_seq START WITH 1;

CREATE TABLE order_status
(
    id VARCHAR(9) PRIMARY KEY NOT NULL,
    status VARCHAR(12) DEFAULT 'NEW',
    FOREIGN KEY (id) REFERENCES orders (id)
);
CREATE TABLE order_times
(
    id bigint PRIMARY KEY DEFAULT nextval('times_id_seq'),
    id_order VARCHAR(9) NOT NULL,
    status VARCHAR(12),
    time TIMESTAMP,
    FOREIGN KEY (id_order) REFERENCES order_status (id)  ON DELETE CASCADE
);
CREATE UNIQUE INDEX order_times_idx ON order_times (id_order, status);


CREATE TABLE description_status
(
    id VARCHAR(13) PRIMARY KEY NOT NULL,
    type VARCHAR(13) NOT NULL DEFAULT 'NEW',
    status VARCHAR(13) NOT NULL DEFAULT 'NEW',
    is_techno_product INTEGER,
    designer_name VARCHAR,
    is_parsing INTEGER DEFAULT 0,
    FOREIGN KEY (id) REFERENCES descriptions (id)
);


CREATE TABLE description_times
(
    id bigint PRIMARY KEY DEFAULT nextval('times_id_seq'),
    id_description VARCHAR(13) NOT NULL,
    status VARCHAR(13) NOT NULL,
    time TIMESTAMP,
    FOREIGN KEY (id_description) REFERENCES description_status (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX description_times_idx ON description_times (id_description, status);


/*CREATE TABLE invoice
(
  iddoc VARCHAR(9) PRIMARY KEY NOT NULL,
  docno VARCHAR(10),
  id_order VARCHAR(9),
  time_invoice TIMESTAMP WITHOUT TIME ZONE,
  time22 BIGINT,
  price NUMERIC(14,3)
);

CREATE TABLE invoice_descr
(
  id VARCHAR(9) PRIMARY KEY NOT NULL,
  id_invoice VARCHAR(9),
  id_order VARCHAR(9),
  id_tmc VARCHAR(9),
  docno_invoice VARCHAR(10),
  time_invoice TIMESTAMP WITHOUT TIME ZONE,
  quantity INT,
  payment NUMERIC(14,3)
);

CREATE TABLE manufacture
(
  id VARCHAR(13) PRIMARY KEY NOT NULL,
  iddoc VARCHAR(9),
  position INTEGER,
  docno VARCHAR(10),
  id_order VARCHAR(9),
  time_manuf TIMESTAMP WITHOUT TIME ZONE,
  time21 BIGINT,
  quantity INTEGER,
  id_tmc VARCHAR(9),
  descr_second VARCHAR,
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  embodiment VARCHAR(9),
  FOREIGN KEY (id_order) REFERENCES orders (iddoc) ON DELETE CASCADE
);

CREATE TABLE manufacture_techno
(
  id VARCHAR(13) PRIMARY KEY NOT NULL,
  id_manuf VARCHAR(9),
  docno VARCHAR(10),
  id_tmc VARCHAR(9),
  id_order VARCHAR(9),
  position INTEGER,
  time_manuf TIMESTAMP,
  quantity INTEGER,
  descr_second VARCHAR,
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  embodiment VARCHAR(9)
);