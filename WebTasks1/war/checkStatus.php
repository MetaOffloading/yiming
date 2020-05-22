<?php
require 'db.php';
$participantCode=$_POST['participantCode'];
$experimentCode=$_POST['experimentCode'];
$version=$_POST['version'];

$db = new mysqli($host, $user, $password, $database);

if (mysqli_connect_errno()) {
	printf("DB: error: %s", mysqli_connect_error());
	exit();
}

$sql = "SELECT * FROM status WHERE participantCode=\"" . $participantCode . "\" AND experimentCode=\"" . $experimentCode . "\" AND version=\"" . $version . "\"";

if($result = mysqli_query($db,$sql)) {
	if (mysqli_num_rows($result)==0) {
		echo ("unknown");
	} else {
		$row = mysqli_fetch_array($result);	
		echo ($row['status']);
	}
} else {
	echo("Query error");
}

mysqli_close($db);
?>