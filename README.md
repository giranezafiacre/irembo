# irembo
project to enhance user account management features in ZPlatform to support rapid growth.  Priorities include improving onboarding, scalability, data management, and security.  Objective is a highly available, performant application capable of handling concurrent requests, scaling user data, and ensuring top-notch security measures.

# Prerequisites
- Java version above 11
- postgres
- add irembo as database

# Installation
- Install Java
- open editor(preferably IntelliJ)

# Usage
- login
- logout
- add 2FA using sms OTP
- signup

# API Documentation

This API provides endpoints for managing user accounts and performing related operations.

## Create User [POST]
Creates a new user account.

URL: /api/v1/auth/create\
Method: POST\
Request Body:\
json\

{\
  "username": "exampleuser",\
  "password": "password123",\
  ...
}\
Response:\
json\
{
  "userId": "123456",\
  "message": "User created successfully"\
}

## Authenticate User [POST]
Authenticates a user and generates an authentication token.

URL: /api/v1/auth/login\
Method: POST\
Request Body:\
json\
{
  "username": "exampleuser",\
  "password": "password123"\
}
Response:\
json\n
{
  "token":\ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "message": "Login successful"\
}

## Verify OTP [POST]
Verifies a one-time password (OTP) for a user.

URL: /api/v1/auth/otp\
Method: POST\
Request Body:\
{\
  "email": "user@example.com",\
  "otp": "123456"\
}
Response:
{\
  "message": "OTP verified successfully"\
}

## Submit Documents for Verification [POST]
Submits user documents for verification.

URL: /api/v1/verification/documents\
Method: POST\
Request Body:\
{
  "nationalID": "1234567890",\
  "documentPhoto": "base64-encoded-image"\
}
Response:
{
  "verificationId": "987654",\
  "status": "pending",\
  "message": "Documents submitted successfully"\
}

## Get All Submitted Documents [GET]
Retrieves a list of all submitted documents for verification. (Authorization required)

URL: /api/v1/verification/all\
Method: GET\
Authorization: Bearer Token\
Response:

[
{    "verificationId": "987654",    "nationalID": "1234567890",    "documentPhoto": "base64-encoded-image",    "status": "pending"  },  ...]

## Verify User Account [POST]
Verifies a user's account. (Authorization required)

URL: /api/v1/verification/verify\
Method: POST\
Authorization: Bearer Token\
Request Body:\
{\
  "userId": "123456"\
}\
Response:\
{\
  "message": "User account verified successfully"\
}\

## Create Admin [POST]
Creates an admin user account.

URL: /api/v1/auth/admin\
Method: POST\
Request Body:\
{\
  "username": "adminuser",\
  "password": "adminpassword",\
  ...\
}\
Response:
{
  "adminId": "789012",\
  "message": "Admin user created successfully"\
  ...\
  }
