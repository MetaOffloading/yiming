<?php
require 'db.php';
$participantCode=$_POST['participantCode'];

$db = new mysqli($host, $user, $password, $database);

if (mysqli_connect_errno()) {
	printf("DB: error: %s", mysqli_connect_error());
	exit();
}

$sql = "SELECT * FROM status WHERE participantCode=\"" . $participantCode . "\" AND status=\"finished\"";

if($result = mysqli_query($db,$sql)) {
	if (mysqli_num_rows($result)==0) {
		echo ("unknown");
	} else {
		$row = mysqli_fetch_array($result);	
		echo ("exists");
	}
} else {
	echo("Query error");
}

mysqli_close($db);
?>