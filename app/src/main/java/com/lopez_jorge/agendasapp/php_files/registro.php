<?php
header('Content-Type: application/json');

// Datos de conexión
$servername = "db.cvmci2wemkun.us-east-1.rds.amazonaws.com";
$username = "admin";
$password = "adminadmin";
$dbname = "agenda";

// Conectar a MySQL
$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "error" => "Error de conexión"]);
    exit();
}

// Recibir datos desde Android
$user = $_POST['usuario'];
$email = $_POST['email'];
$pass = $_POST['password'];

// (Opcional) Hashear contraseña
$hashedPassword = password_hash($pass, PASSWORD_DEFAULT);

// Insertar en la base de datos
$stmt = $conn->prepare("INSERT INTO usuarios (usuario, email, password) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $user, $email, $hashedPassword);

if ($stmt->execute()) {
    echo json_encode(["success" => true]);
} else {
    echo json_encode(["success" => false, "error" => $stmt->error]);
}

$stmt->close();
$conn->close();
?>