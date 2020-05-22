<?php
require 'db.php';
$dataType=$_POST['dataType'];
$participantCode=$_POST['participantCode'];
$experimentCode=$_POST['experimentCode'];
$version=$_POST['version'];
$sessionKey=$_POST['sessionKey'];
$data=$_POST['data'];

$db = new mysqli($host, $user, $password, $database);

if (mysqli_connect_errno()) {
	printf("DB: error: %s", mysqli_connect_error());
	exit();
}

$stmt = $db->prepare("INSERT INTO data VALUES (?,?,?,?,?,?,NOW(),CURTIME())");
$stmt->bind_param("sssiss", $dataType, $participantCode, $experimentCode, $version, $sessionKey, $data);


if ($stmt->execute()) {
	echo("OK");
} else {
	echo("Error");
}

mysqli_close($db);
?>
	