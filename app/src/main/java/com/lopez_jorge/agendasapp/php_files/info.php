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
ubuntu@ip-172-31-31-197:/var/www/html$ ls
index.html  info.php  register.php  register.php.save  registro.php  registro.php.save
ubuntu@ip-172-31-31-197:/var/www/html$ cat info.php 
<?php
header('Content-Type: application/json');

$servername = "db.cvmci2wemkun.us-east-1.rds.amazonaws.com";
$username = "admin";
$password = "adminadmin";
$dbname = "agenda";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    echo json_encode(["success" => false, "error" => "Error de conexión"]);
    exit();
}

$user = $_POST['usuario'];
$pass = $_POST['password'];

$stmt = $conn->prepare("SELECT * FROM usuarios WHERE usuario = ? AND password = ?");
$stmt->bind_param("ss", $user, $pass);
$stmt->execute();
$result = $stmt->get_result();

echo json_encode(["success" => $result->num_rows > 0]);

$stmt->close();
$conn->close();
?>