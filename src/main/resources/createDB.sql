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

CREATE TABLE developer
(
    pseudo_name VARCHAR(28) PRIMARY KEY NOT NULL,
    name VARCHAR
);
insert into developer (pseudo_name, name) VALUES
('Sergienko', 'Сергієнко О. І.'),
('Mosienko', 'Мосієнко В. І.'),
('Diachok', 'Дячок Д. М.'),
('aser', 'Сергієнко О. І.'),
('Alex', 'Сергієнко О. І.'),
('Drogovoz', 'Дроговоз'),
('aleksChay', 'Сергієнко О. І.'),
('WIN-S73248V9KQV', 'Дячок Д. М.'),
('KEV-PC', 'Дячок Д. М.');

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
  status VARCHAR(12) DEFAULT 'NEW',
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
  type VARCHAR(13) NOT NULL DEFAULT 'NEW',
  status VARCHAR(13) NOT NULL DEFAULT 'NEW',
  designer_name VARCHAR,
  FOREIGN KEY (id_order) REFERENCES orders (id) ON DELETE CASCADE
);

create sequence times_id_seq START WITH 1;

CREATE TABLE order_time
(
    id bigint PRIMARY KEY DEFAULT nextval('times_id_seq'),
    id_order VARCHAR(9) NOT NULL,
    status VARCHAR(12),
    time TIMESTAMP,
    FOREIGN KEY (id_order) REFERENCES orders (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX order_times_idx ON order_time (id_order, status);


CREATE TABLE description_time
(
    id bigint PRIMARY KEY DEFAULT nextval('times_id_seq'),
    id_description VARCHAR(13) NOT NULL,
    status VARCHAR(13) NOT NULL,
    time TIMESTAMP,
    FOREIGN KEY (id_description) REFERENCES descriptions (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX description_times_idx ON description_time (id_description, status);