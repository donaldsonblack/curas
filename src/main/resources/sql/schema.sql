CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  cognito_sub UUID NOT NULL UNIQUE,
  name TEXT NOT NULL,
  email TEXT NOT NULL UNIQUE,
  departments JSONB NOT NULL DEFAULT '[]',
  role TEXT NOT NULL DEFAULT 'user',
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE department (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE
);

CREATE TABLE equipment (
  id SERIAL PRIMARY KEY,
  department_id INTEGER REFERENCES department(id) ON DELETE CASCADE,
  serial TEXT,
  model TEXT,
  name TEXT
);

CREATE TABLE checklists (
  id SERIAL PRIMARY KEY,
  type TEXT NOT NULL,
  department_id INTEGER REFERENCES department(id) ON DELETE SET NULL,
  equipment_id INTEGER REFERENCES equipment(id) ON DELETE SET NULL,
  author_id INTEGER REFERENCES users(id) ON DELETE SET NULL,
  created TIMESTAMP DEFAULT now(),
  questions JSONB
);

CREATE TABLE record (
  id SERIAL PRIMARY KEY,
  checklist_id INTEGER REFERENCES checklists(id) ON DELETE CASCADE,
  author_id INTEGER REFERENCES users(id) ON DELETE SET NULL,
  created TIMESTAMP DEFAULT now(),
  answers JSONB
);