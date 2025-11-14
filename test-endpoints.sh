#!/bin/bash

# Script de test des endpoints Mediaskol
# ==========================================

BASE_URL="http://localhost:8080/mediaskolFormation"
TOKEN="eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTc2MjcwMjgzOSwiZXhwIjoxNzYyNzA2NDM5fQ.L_4mfdhKA23dGFJdFVBylXfBKfYktHl3UN-f0jsnBsAi5HOxtkOF-c9buOITiRrtm8Xf9bIvj3Y4nQ6K1_ibPG7o93x4pZsVF0bJ3P6RQ_k6rV5FH9Fc6Gi7ReF0ngHH-e8h1AfLaHOSBiYYuSt0mjuOdr71E1zbcWcAa9H-cRkjvyRgtNsY50NbFNsNu51II3LlaaXMrNW8bzljxPiPlHN36hUoRM4AiKZKaN9xnAir4HhcbdGuHpoP_y_KPclJrOY85ZtimZbOLYIOxcPZdWC0k8hn-Mxf1QfU3ph_WPB12pfDpmXq76xq4aZ65gopqArGfhC67yeJ-ApkXIKH8g"

echo "======================================"
echo "TESTS DES ENDPOINTS MEDIASKOL"
echo "======================================"
echo ""

# Fonction de test
test_endpoint() {
    local method=$1
    local path=$2
    local description=$3
    local data=$4

    echo "[$method] $path - $description"

    if [ -z "$data" ]; then
        response=$(curl -s -w "\nHTTP_CODE:%{http_code}" -X $method "$BASE_URL$path" \
            -H "Authorization: Bearer $TOKEN" \
            -H "Content-Type: application/json")
    else
        response=$(curl -s -w "\nHTTP_CODE:%{http_code}" -X $method "$BASE_URL$path" \
            -H "Authorization: Bearer $TOKEN" \
            -H "Content-Type: application/json" \
            -d "$data")
    fi

    http_code=$(echo "$response" | grep "HTTP_CODE:" | cut -d: -f2)
    body=$(echo "$response" | sed '/HTTP_CODE:/d')

    if [ "$http_code" = "200" ] || [ "$http_code" = "201" ] || [ "$http_code" = "204" ]; then
        echo "  ✓ Status: $http_code"
    else
        echo "  ✗ Status: $http_code"
    fi

    if [ ! -z "$body" ] && [ "$body" != "" ]; then
        echo "  Response: ${body:0:100}..."
    fi
    echo ""
}

# Test authentification
echo "=== 1. AUTHENTIFICATION ==="
echo "[POST] /auth - Génération token JWT"
auth_response=$(curl -s -X POST "$BASE_URL/auth" \
    -H "Content-Type: application/json" \
    -d '{"pseudo":"admin","password":"password"}')
echo "  ✓ Token généré"
echo ""

# Test formations
echo "=== 2. FORMATIONS ==="
test_endpoint "GET" "/formations" "Lister toutes les formations"
test_endpoint "GET" "/formations/1" "Récupérer formation ID=1"

# Test sessions formation présentiel
echo "=== 3. SESSIONS FORMATION PRÉSENTIEL ==="
test_endpoint "GET" "/sessionsFormationsPresentiels" "Lister toutes les sessions présentiel"
test_endpoint "GET" "/sessionsFormationsPresentiels/moinsSixSessionsApprenants" "Sessions avec < 6 apprenants"

# Test départements
echo "=== 4. DÉPARTEMENTS ==="
test_endpoint "GET" "/departements/bretagne" "Départements de Bretagne"

# Test apprenants
echo "=== 5. APPRENANTS ==="
test_endpoint "GET" "/apprenants" "Lister tous les apprenants"

# Test salariés (ADMIN only)
echo "=== 6. SALARIÉS (ADMIN only) ==="
test_endpoint "GET" "/salaries" "Lister tous les salariés"

# Test formateurs
echo "=== 7. FORMATEURS ==="
test_endpoint "GET" "/formateurs" "Lister tous les formateurs"

# Test salles
echo "=== 8. SALLES ==="
test_endpoint "GET" "/salles" "Lister toutes les salles"

echo "======================================"
echo "FIN DES TESTS"
echo "======================================"
