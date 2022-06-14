<?php
	$host = "sql204.epizy.com";
	$username = "epiz_31954340";
	$password = "pvUseRUIjiwTS";
	$database = "epiz_31954340_sp";

	$conn = mysqli_connect($host,$username,$password,$database);
	mysqli_query($conn,"SET NAMES 'utf8'");
?>