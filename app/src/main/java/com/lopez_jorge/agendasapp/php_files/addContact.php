<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// Configuración de conexión a MySQL (RDS)
$servername = "db.cvmci2wemkun.us-east-1.rds.amazonaws.com";
$username = "admin";
$password = "adminadmin";
$dbname = "agenda";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Error de conexión"]);
    exit();
}

// Obtener datos del POST
$name = $_POST['name'] ?? '';
$email = $_POST['email'] ?? '';
$address = $_POST['address'] ?? '';
$phone = $_POST['phone'] ?? '';

// Validación básica
if (empty($name) || empty($email)) {
    http_response_code(400);
    echo json_encode(["success" => false, "message" => "Faltan datos obligatorios"]);
    exit();
}

// Insertar en la tabla
$stmt = $conn->prepare("INSERT INTO contacts (name, email, address, phone) VALUES (?, ?, ?, ?)");
$stmt->bind_param("ssss", $name, $email, $address, $phone);

if ($stmt->execute()) {
    echo json_encode(["success" => true, "message" => "Contacto agregado correctamente"]);
} else {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Error al insertar: " . $conn->error]);
}

$stmt->close();
$conn->close();
?>