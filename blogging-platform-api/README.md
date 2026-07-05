# blogging-platform-api
This is a RESTful API for a simple blogging platform, built as a practical exercise to master backend development and API design.

## Project Details
This project is based on the [roadmap.sh Blogging Platform API challenge](https://roadmap.sh/projects/blogging-platform-api).

## Prerequisites
Before running the application, ensure you have the following installed:
* Java (JDK 21)
* Maven
* Git
* Docker & Docker Compose

## How to run:
I assume you have java, maven, git, and docker installed on your computer

### Clone the repository
First clone the repository on your computer
```bash
git clone https://github.com/adilkhanalimberdi/blogging-platform-api.git
cd blogging-platform-api
```

### Run docker image for PostgreSQL DB:
Open terminal or command-prompt and run this command:
```bash
docker compose up -d
```

### Run the application:
In the terminal run this command and wait until it says "Tomcat server started on ...":
```bash
./mvnw spring-boot:run
```

### Check the application:
You can check it with whatever you want, you can check GET requests in the browser.
But it's recommended to use API testing tools like Postman, SoapUI, HTTPie, or any other online tools.

Go to the browser and go to `http://localhost:8080/posts`, you should see empty JSON array.

## Work with the API
Now you can test the API with these endpoints

### Create a new Post
```http request
POST /posts
{
  "title": "My First Post",
  "content": "This is the content of my first post.",
  "category": "Technology",
  "tags": ["java", "spring", "postgresql"]
}
```

You should get a:
- 201 CREATED (with created post in the response body) if the post was created
- 400 BAD REQUEST if there was any validation error

### Update an existing Post
```http request
PUT /posts/{id}
{
  "title": "My Updated Post",
  "content": "This is the content of my updated post.",
  "category": "Technology",
  "tags": ["java", "spring", "postgresql", docker"]
}
```

You should get a:
- 200 OK (with updated post in the response body) if the post was updated
- 400 BAD REQUEST if there was any validation errors
- 404 NOT FOUND if the specified id was not found

### Delete an existing Post
```http request
DELETE /posts/{id}
```

You should get a:
- 204 NO CONTENT if the post was deleted successfully
- 404 NOT FOUND if the specified id was not found

### Get all Posts
```http request
GET /posts
```

You should get a:
- 200 OK (with the array of posts in the response body) if everything was fine

### Get all Posts by search term
```http request
GET /posts?term={term}
```

You should get a:
- 200 OK (with the array of posts filtered by search term in the response body)

### Get existing Post
```http request
GET /posts/{id}
```

You should get a:
- 200 OK (with found post in the response body) if the post was found
- 404 NOT FOUND if the specified id was not found



