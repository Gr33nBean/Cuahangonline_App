<?php 
	include "connect.php";
	// $json='[{"gisanpham":6990000,"madonhang":"5","soluongsanpham":1,"tensanpham":"Laptop Lenovo IdeaPad 110","masanpham":20},{"gisanpham":29970000,"madonhang":"5","soluongsanpham":3,"tensanpham":"Điện thoại Sony Xperia Z5 Dual","masanpham":21}]';
	$json=$_POST['json'];
	$data = json_decode($json,true);
	foreach ($data as $value) {
		$madonhang = $value['madonhang'];
		$masanpham = $value['masanpham'];
		$tensanpham = $value['tensanpham'];
		$giasanpham = $value['giasanpham'];
		$soluongsanpham = $value['soluongsanpham'];
		$query ="INSERT INTO chitietdonhang(id,madonhang,masanpham,tensanpham,giasanpham,soluongsanpham) VALUES (null,'$madonhang','$masanpham','$tensanpham','$giasanpham','$soluongsanpham')";
		$Dta = mysqli_query($conn,$query);
	}
	if ($Dta) {
		echo "1";
	}
	else
	{
		echo "0";
	}
?>