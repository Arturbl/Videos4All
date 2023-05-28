# Videos4All Application Documentation

This documentation provides an overview of the endpoints available in the application.

## AuthController

### Login

- Method: POST
- Endpoint: `/auth/login`
- Description: Authenticate a user and generate an access token.

## UserController

### Find All Users

- Method: GET
- Endpoint: `/findAll`
- Description: Retrieve a list of all users.

### Check User Validity

- Method: GET
- Endpoint: `/isValidUser/{username}/{password}`
- Description: Check if a user with the specified username and password exists.

### Add User

- Method: POST
- Endpoint: `/addUser/{username}/{password}`
- Description: Add a new user with the specified username and password.

## VideoController

### Upload Video

- Method: POST
- Endpoint: `/video/upload`
- Description: Upload a video file.

### Find All Videos

- Method: GET
- Endpoint: `/video/findAll`
- Description: Retrieve a list of all videos.

### Find Video by Name

- Method: GET
- Endpoint: `/video/find/{name}`
- Description: Find a video by its name.

### How to run database


1. Open a terminal or command prompt.
2. Navigate to the root directory of the project. [/PostgresSQL]
3. Run the following command to build the database container:
    - docker-compose build --no-cache
4. Run the following command to start the database container:
    - docker-compose up -d


