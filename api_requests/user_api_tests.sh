#!/bin/bash

# Library Management System - User API Test Script
# Make sure to replace YOUR_JWT_TOKEN with actual token from login response

BASE_URL="http://localhost:8080"
JWT_TOKEN="YOUR_JWT_TOKEN"

echo "=== Library Management System - User API Tests ==="
echo ""

# Function to make API calls
make_request() {
    local method=$1
    local url=$2
    local data=$3
    local headers=$4
    
    echo "Making $method request to $url"
    if [ -n "$data" ]; then
        curl -X $method "$url" -H "Content-Type: application/json" $headers -d "$data"
    else
        curl -X $method "$url" $headers
    fi
    echo ""
    echo "---"
}

echo "1. Testing User Login..."
# Login as admin
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{"username":"admin","password":"admin123"}')

echo "Login Response: $LOGIN_RESPONSE"
echo ""

# Extract token (you'll need to manually copy this token for subsequent requests)
echo "2. Getting All Users (requires JWT token)..."
make_request "GET" "$BASE_URL/api/users" "" "-H \"Authorization: Bearer $JWT_TOKEN\""

echo "3. Creating a New User..."
make_request "POST" "$BASE_URL/api/users" '{
    "username": "test.user",
    "email": "test.user@lms.com",
    "password": "password123",
    "roleId": 3,
    "firstName": "Test",
    "lastName": "User"
}' "-H \"Authorization: Bearer $JWT_TOKEN\""

echo "4. Getting User by ID..."
make_request "GET" "$BASE_URL/api/users/1" "" "-H \"Authorization: Bearer $JWT_TOKEN\""

echo "5. Updating a User..."
make_request "PUT" "$BASE_URL/api/users/4" '{
    "username": "updated.user",
    "email": "updated.user@lms.com",
    "firstName": "Updated",
    "lastName": "User",
    "roleId": 2,
    "isActive": true
}' "-H \"Authorization: Bearer $JWT_TOKEN\""

echo "6. Testing User Registration..."
make_request "POST" "$BASE_URL/api/auth/register" '{
    "username": "new.registrant",
    "email": "new.registrant@lms.com",
    "password": "password123",
    "roleId": 3,
    "firstName": "New",
    "lastName": "Registrant"
}' ""

echo "7. Testing Invalid Login..."
make_request "POST" "$BASE_URL/api/auth/login" '{
    "username": "admin",
    "password": "wrongpassword"
}' ""

echo "=== Test Script Completed ==="
echo ""
echo "Note: Replace 'YOUR_JWT_TOKEN' with the actual token from the login response"
echo "To get a token, run: curl -X POST $BASE_URL/api/auth/login -H \"Content-Type: application/json\" -d '{\"username\":\"admin\",\"password\":\"admin123\"}'"
