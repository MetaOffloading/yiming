<?php
require 'db.php';
$participantCode=$_POST['participantCode'];
$experimentCode=$_POST['experimentCode'];
$version=$_POST['version'];
$status=$_POST['status'];

$db = new mysqli($host, $user, $password, $database);

if (mysqli_connect_errno()) {
	printf("DB: error: %s", mysqli_connect_error());
	exit();
}

$sql = "SELECT * FROM status WHERE participantCode=\"" . $participantCode . "\" AND experimentCode=\"" . $experimentCode . "\" AND version=\"" . $version . "\"";

if($result = mysqli_query($db,$sql)) {
	if (mysqli_num_rows($result)==0) {
		//unknown participant, add new entry to status table
		$stmt = $db->prepare("INSERT INTO status VALUES (?,?,?,?)");
		$stmt->bind_param("ssis", $participantCode, $experimentCode, $version, $status);
		
		if($stmt->execute()) {
			echo("OK");
		} else {
			echo("Database entry error");
		}
	} else {
		$stmt = $db->prepare("UPDATE status SET status=(?) WHERE participantCode=\"" . $participantCode . "\" AND experimentCode=\"" . $experimentCode . "\" AND version=\"" . $version . "\"");
		$stmt->bind_param("s", $status);
		if($stmt->execute()) {
			echo("OK");
		} else {
			echo("Database update error");
		}
	}
} else {
	echo("Database query error");
}

mysqli_close($db);
?>
