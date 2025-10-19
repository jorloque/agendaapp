<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');

$servername = "db.cvmci2wemkun.us-east-1.rds.amazonaws.com"; 
$username = "admin";
$password = "adminadmin";
$database = "agenda";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_error) {
    echo json_encode(["success" => false, "error" => "DB connection error: " . $conn->connect_error]);
    exit();
}

// Espera POST: id, name, email, address, phone
$id      = isset($_POST['id']) ? intval($_POST['id']) : 0;
$name    = isset($_POST['name']) ? trim($_POST['name']) : "";
$email   = isset($_POST['email']) ? trim($_POST['email']) : "";
$address = isset($_POST['address']) ? trim($_POST['address']) : "";
$phone   = isset($_POST['phone']) ? trim($_POST['phone']) : "";

if ($id <= 0 || $name === "" || $email === "") {
    echo json_encode(["success" => false, "error" => "Missing required fields"]);
    exit();
}

$stmt = $conn->prepare("UPDATE contacts SET name=?, email=?, address=?, phone=? WHERE id=?");
$stmt->bind_param("ssssi", $name, $email, $address, $phone, $id);
$ok = $stmt->execute();

echo json_encode(["success" => $ok]);

$stmt->close();
$conn->close();
