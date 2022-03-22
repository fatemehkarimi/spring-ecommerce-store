Feature: User Login To Account
	a registered user must be able to login with email and password

	Scenario Outline: User Can Login To Account
		Given i send a request to URL "/api/auth/login" with email = "<email>" and password = "<password>"
		Then the result is "<result>"

		Examples:
			| email 						| password	| result		|
			| fatemehkarimi.1998@yahoo.com	| admin		| authenticated	|
			| mohammadmanafi@yahoo.com		| admin		| authenticated	|


