CREATE TABLE categories (
  id serial NOT NULL,
  name varchar(510) DEFAULT NULL,
  
  PRIMARY KEY (id)
  
);

CREATE TABLE jokes (
  id serial NOT NULL,
  content text DEFAULT NULL,
  likes integer DEFAULT 0,
  dislikes integer DEFAULT 0,
  category_id integer DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) 
  REFERENCES categories (id) 
);

CREATE TABLE users (
  email varchar(255) NOT NULL,
  name varchar(510) NOT NULL,
  PASSWORD varchar(255) DEFAULT NULL,
  PRIMARY KEY (email),
  UNIQUE(email)
  
);

CREATE TABLE roles (
  name varchar(255) NOT NULL,
  PRIMARY KEY (name),
  UNIQUE(name)
);

CREATE TABLE user_roles (
  user_email varchar(255) NOT NULL,
  role_name varchar(255) NOT NULL
  
);
