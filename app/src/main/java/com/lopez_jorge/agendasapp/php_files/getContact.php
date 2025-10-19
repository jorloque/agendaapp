<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *'); // permite peticiones desde tu app Android

// 📌 Configura tus datos de conexión a la base de datos
$servername = "db.cvmci2wemkun.us-east-1.rds.amazonaws.com";
$username = "admin";
$password = "adminadmin";
$database = "agenda";

// 🔌 Conexión a la base de datos
$conn = new mysqli($servername, $username, $password, $database);

// 🚨 Verificar si hubo error
if ($conn->connect_error) {
    echo json_encode(["success" => false, "error" => "Error de conexión: " . $conn->connect_error]);
    exit();
}

// 🧾 Consulta para obtener todos los contactos
$sql = "SELECT name, email, address, phone FROM contacts";
$result = $conn->query($sql);

$contacts = [];

if ($result && $result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $contacts[] = $row;
    }
}

// 📤 Respuesta JSON
echo json_encode($contacts);

// 🚪 Cerrar conexión
$conn->close();
?>