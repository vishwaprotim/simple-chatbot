# Simple REST Chatbot API

A lightweight Spring Boot service that exposes a single chat endpoint backed by Google Gemini via Spring AI.

## Overview

This project provides a simple REST API for sending a user prompt and receiving a model-generated response. It is built with:

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring AI Google GenAI Chat Client
- Gradle wrapper for builds

## Features

- Single POST endpoint: `/prompt`
- Uses Google Gemini chat model via `spring-ai-starter-model-google-genai`
- Reads API key from environment variable
- Safe default configuration using Spring Boot conventions

## Requirements

- macOS, Linux, or Windows
- Java 21 JDK
- Internet access for Google AI API calls
- A valid Google Gemini API key

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/vishwaprotim/simple-chatbot.git
cd simple-chatbot
```

### 2. Configure the Gemini API key

This application reads the Gemini API key from the environment variable `GEMINI_API_KEY`.

[Get your Gemini API key here.](https://aistudio.google.com/api-keys)

After generating the API key, set the environment variable as applicable.

For macOS / Linux:

```bash
export GEMINI_API_KEY="your-google-gemini-api-key"
```

For Windows PowerShell:

```powershell
$env:GEMINI_API_KEY = "your-google-gemini-api-key"
```

### 3. Confirm Java 21 is available

```bash
./gradlew --version
```

If you do not have Java 21 installed, install it and ensure `JAVA_HOME` points to Java 21.

## Build

Use the Gradle wrapper to build the project:

```bash
./gradlew clean build
```

This will compile the code, run tests, and produce a Spring Boot JAR.

## Run

### Run with Gradle

```bash
./gradlew bootRun
```

### Run the packaged JAR

```bash
java -jar build/libs/simple-chatbot-0.0.1-SNAPSHOT.jar
```

The service starts on port `8080` by default.

## API Usage

### Endpoint

- URL: `http://localhost:8080/prompt`
- Method: `POST`
- Content-Type: `text/plain` or `application/json`
- Body: raw prompt text or JSON string body containing the user question

### Example using `curl`

```bash
curl -X POST http://localhost:8080/prompt \
  -H "Content-Type: text/plain" \
  -d "Hello, how are you?"
```

Expected response:

- HTTP `200 OK`
- Response body: model-generated answer text

### Example request body

Because the controller accepts a raw string request body, the body can be plain text. If your client sends JSON, ensure the JSON is quoted correctly.

```bash
curl -X POST http://localhost:8080/prompt \
  -H "Content-Type: application/json" \
  -d '"What is the weather today?"'
```

### Common Issues and Errors

#### Runtime Exception - Quota Exceeded

Quota exceeded for metric: generativelanguage.googleapis.com/generate_content_free_tier_input_token_count, limit: 0, model: gemini-2.0-flash

#### Resolution

The limit: 0 error appears because your Google Cloud project does not have Free Tier quotas activated.

Since March 2024, Google requires linking a billing account even for Gemini Free Tier.
If billing is not enabled, the free quotas for:

- generate_content_free_tier_requests
- input_token_count
- output_token_count

are all 0, which causes the 429 ResourceExhausted error even on a new project.

After you link a billing account, the free-tier quotas (50 requests/day, token limits, etc.) become available and the API will work.

#### Runtime Exception - Model No Longer Available

ClientException: 404 . This model models/gemini-2.0-flash-001 is no longer available to new users. Please update your code to use a newer model for the latest features and improvements.

#### Resolution

Check latest models and verify you are not using a deprecated one. If unsure about the version you can use the latest versions, like **gemini-flash-latest**

## Configuration

The application configuration is located in `src/main/resources/application.yaml`.

Key settings:

```yaml
spring:
  application:
    name: simple-chatbot
  ai:
    google:
      genai:
        api-key: ${GEMINI_API_KEY}
        model: gemini-pro
        chat.options.model: gemini-flash-latest
```

- `GEMINI_API_KEY` must be set in your environment.
- `model` and `chat.options.model` specify the Gemini models used for API calls.

## Testing

Run the unit test suite with:

```bash
./gradlew test
```

The project includes a simple Spring Boot context load test.

## Notes

- This project is intentionally minimal and designed as a starter chatbot API.
- If Google Gemini returns quota or model availability errors, verify your Google Cloud project billing and API access.
- You can change the default port or add additional endpoints by modifying Spring Boot settings and controller classes.

## Project Structure

- `src/main/java`: application source code
- `src/main/resources/application.yaml`: runtime configuration
- `src/test/java`: test code
- `build.gradle`: Gradle build configuration

## License

This repository does not include an explicit license file.
