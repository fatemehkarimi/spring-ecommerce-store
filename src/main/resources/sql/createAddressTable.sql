CREATE TABLE public.address(
	userId uuid,
	id serial,
	city varchar(255) NOT NULL,
	address varchar(255) NOT NULL,
	zipCode varchar(20) NOT NULL,
	FOREIGN KEY(userId) REFERENCES public.user,
	PRIMARY KEY(userId, id)
);