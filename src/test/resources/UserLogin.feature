Feature: User Login To Account
	a registered user must be able to login with email and password

	Scenario Outline: User Can Login To Account
		Given a user with email = "<email>" and password = "<password>"
		When i try to login to my account

		Examples:
			| email 					| password	|
			| fatemehkarimi@gmail.com	| pass123	|
			| mohammadmanafi@yahoo.com	| pass123	|


