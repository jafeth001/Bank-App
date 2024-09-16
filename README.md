# Bank APP API
 ## 1. Authentication & Authorization

### The authentication service provides endpoints for user registration, account activation, token generation, user login, password reset, and administrative user management. The following are key endpoints for authentication and authorization in the system: User Registration

### Endpoint: /auth/user/signup
### Method: POST
### Description: Registers a new user and sends an activation email with a token.
### Exceptions:
    ConflictException: Thrown if the user with the provided email already exists.

Activate Account

Endpoint: /auth/verify-account
Method: PUT
Description: Activates the user's account using the email and token sent via email.
Exceptions:
    NoSuchException: Thrown if the user or token is invalid.

Regenerate Token

Endpoint: /auth/regenarate-token
Method: PUT
Description: Regenerates a token for account activation and sends it to the user's email.
Exceptions:
    NoSuchException: Thrown if the user is not found.

User Login

Endpoint: /auth/signin
Method: POST
Description: Authenticates the user and returns a JWT token for subsequent requests.
Exceptions:
    ConflictException: Thrown if the account is not active or if the password is incorrect.
    NoSuchException: Thrown if the user does not exist.

Forgot Password

Endpoint: /auth/forgot-password
Method: PUT
Description: Sends a token to the user's email for password reset.
Exceptions:
    NoSuchException: Thrown if the user is not found.

Reset Password

Endpoint: /auth/reset-password
Method: PUT
Description: Resets the user's password using a token.
Exceptions:
    ConflictException: Thrown if the passwords do not match.

Update Password

Endpoint: /auth/update-password
Method: PUT
Description: Allows an authenticated user to update their password.
Exceptions:
    ConflictException: Thrown if the current password is incorrect or if the new passwords do not match.
    NoSuchException: Thrown if the user is not found.

Admin Registration

Endpoint: /auth/admin/signup
Method: POST
Description: Registers a new admin user.
ConflictException: Thrown if the admin with the provided email already exists.

Kafka Messaging

Endpoint: /auth/publish
Method: POST
Description: Publishes a message to a Kafka topic.

 2. Account Management

Create New Account

Endpoint: /account/create?id=user_id
Method: POST
Description: Create a new bank account and account automatic becomes active.
Exception:
	NoSuchException: If user_id is not available, throw an exception.

Get All Accounts

Endpoint: /account/all
Method: GET
Description: Fetch all accounts.
Exception: 
	NoSuchException: If accounts are not available, throw an exception.

Get Account Details Endpoint: /account?id=account_id Method: GET Description: Fetch details of a specific account. Exception: NoSuchException: If account_id is not available, throw an exception.

Kafka Messaging

Endpoint: /account/publish
Method: POST
Description: Publishes a message to a Kafka topic.

### 3. Transaction Management

Get All Transactions (For Specific Account)

Endpoint: /transaction?id=account_id
Method: GET
Description: Get all transactions for a specific account.
Exception:
	NoSuchException: If account_id is not available, throw an exception.

Get Specific Transaction

Endpoint: /transaction/{transactionId}
Method: GET
Description: Fetch a specific transaction by transaction_id.
Exception: 
	NoSuchException: If transaction_id is not available, throw an exception.

Get All Transactions

Endpoint: /transaction/all
Method: GET
Description: Retrieve all transactions.
Exception: 
	NoSuchException: If no transactions are available, throw an exception.

 4. Transaction Management

Deposit Funds

Endpoint: /transaction/deposit?id=account_id
Method: PUT
Description: Deposit funds into the account.
Exceptions:
    NoSuchException: If account_id and not available, throw an exception.
    ConflictException: If the transfer amount is less than 0.00, throw an exception.
    ConflictException: If the account (identified by account_id) is not active, throw an exception.

Withdraw Funds

Endpoint: /transaction/withdraw?id=account_id
Method: PUT
Description: Withdraw funds from the account.
Exceptions:
    NoSuchException: If account_id and not available, throw an exception.
    ConflictException: If the transfer amount is less than 0.00, throw an exception.
    ConflictException: If the account (identified by account_id) is not active, throw an exception.
    ConflictException: If the transfer amount exceeds the account balance, throw an exception.

Transfer Funds

Endpoint: /transaction/transfer?id=account_id&destId=destination_accountId
Method: PUT
Description: Transfer funds between accounts.
Exceptions:
    NoSuchException: If account_id and destination_accountId not available, throw an exception.
    ConflictException: If the transfer amount is less than 0.00, throw an exception.
    ConflictException: If the account (identified by account_id) is not active, throw an exception.
    ConflictException: If the transfer amount exceeds the account balance, throw an exception.

Kafka Messaging

Endpoint: /transaction/publish
Method: POST
Description: Publishes a message to a Kafka topic.

 5. Admin Management

The admin management endpoints allow administrators to manage user accounts, including activating, deactivating, and deleting accounts. Additionally, admins can retrieve user details, view specific account information, and publish messages to Kafka. Get All Users

Endpoint: /admin/users
Method: GET
Description: Retrieves a list of all registered users in the system.
Exceptions:
    NoSuchException: Thrown if no users are found.

Get Bank Account Details

Endpoint: /admin/account
Method: GET
Description: Fetches details of a specific bank account by account number.
Exceptions:
    NoSuchException: Thrown if the account is not found.

Deactivate Bank Account

Endpoint: /admin/deactivate
Method: PUT
Description: Deactivates a specific bank account.
Exceptions:
    NoSuchException: Thrown if the account is not found.

Activate Bank Account

Endpoint: /admin/activate
Method: PUT
Description: Activates a specific bank account.
Exceptions:
    NoSuchException: Thrown if the account is not found.

Delete Bank Account

Endpoint: /admin/delete
Method: DELETE
Description: Deletes a specific bank account from the system.
Exceptions:
    NoSuchException: Thrown if the account is not found.

Publish Message to Kafka

Endpoint: /admin/publish
Method: POST
Description: Publishes a message to a Kafka topic.

