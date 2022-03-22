Feature: User Login To Account
	a registered user must be able to login with email and password

	Scenario Outline: User Can Login To Account
		Given i send a request to URL "/api/auth/" with email = "<email>" and password = "<password>"
		Then the result is "<result>"

		Examples:
			| email 					| password	| result		|
			| fatemehkarimi@gmail.com	| pass123	| authenticated	|
			| mohammadmanafi@yahoo.com	| pass123	| authenticated	|


