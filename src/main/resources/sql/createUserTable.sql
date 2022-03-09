CREATE TABLE public.user(
	id uuid PRIMARY KEY,
	firstName varchar(255) NOT NULL,
	lastName varchar(255) NOT NULL,
	phoneNumber varchar(10) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	joinDate date DEFAULT CURRENT_DATE
);