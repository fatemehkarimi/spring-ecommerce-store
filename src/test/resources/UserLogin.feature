Feature: User Login To Account
	a registered user must be able to login with email and password

	Scenario Outline: User Can Login To Account
		Given i send a request to URL "/api/auth/login" with email = "<email>" and password = "<password>"
		Then the result is "<result>"

		Examples:
			| email 						| password	| result		|
			| fatemehkarimi.1998@yahoo.com	| admin		| authenticated	|
			| nsk.es@yahoo.com				| admin		| authenticated	|
			| leily.nourbakhsh1999@gmail.com| admin		| authenticated	|
			| person@example.com  			| test123	| access denied	|
			| firstname.lastname@gmail.com	| passs12	| access denied |


