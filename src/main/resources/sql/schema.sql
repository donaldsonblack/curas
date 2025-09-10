CREATE TABLE users (
    id serial PRIMARY KEY,
    cognito_sub uuid NOT NULL UNIQUE,
    name text NOT NULL,
    email text NOT NULL UNIQUE,
    departments jsonb DEFAULT '[]'::jsonb NOT NULL,
    role text DEFAULT 'user'::text NOT NULL,
    created timestamp with time zone DEFAULT now()
);

ALTER TABLE users OWNER TO postgres;

CREATE TABLE department (
    id serial PRIMARY KEY,
    name text NOT NULL UNIQUE
);

ALTER TABLE department OWNER TO postgres;

CREATE TABLE equipment (
    id serial PRIMARY KEY,
    department_id integer REFERENCES department ON DELETE CASCADE,
    serial text,
    model text,
    name text
);

ALTER TABLE equipment OWNER TO postgres;

CREATE TABLE checklists (
    id serial PRIMARY KEY,
    type text NOT NULL,
    department_id integer REFERENCES department ON DELETE SET NULL,
    equipment_id integer REFERENCES equipment ON DELETE SET NULL,
    author_id integer REFERENCES users ON DELETE SET NULL,
    created timestamp DEFAULT now(),
    questions jsonb,
    description text NOT NULL,
    name text NOT NULL
);

ALTER TABLE checklists OWNER TO postgres;

CREATE TABLE record (
    id serial PRIMARY KEY,
    checklist_id integer REFERENCES checklists ON DELETE CASCADE,
    author_id integer REFERENCES users ON DELETE SET NULL,
    created timestamp DEFAULT now(),
    answers jsonb
);

ALTER TABLE record OWNER TO postgres;

