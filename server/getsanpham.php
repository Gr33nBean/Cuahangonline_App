<?php 
	include "connect.php";
	$page = $_GET['page'];
	$idsp = $_POST['idsanpham'];
	$space = 5;
	$limit = ($page -1)*$space;
	$mangsanpham = array();
	$query = "SELECT * FROM `sanpham` WHERE idsanpham = $idsp LIMIT $limit,$space;";
	$data = mysqli_query($conn,$query);

	foreach ($data as $value) {
		$temp = new SanPham($value['id'],$value['tensanpham'],$value['giasanpham'],$value['hinhanhsanpham'],$value['motasanpham'],$value['idsanpham']);
		array_push($mangsanpham,$temp);
	}
	echo json_encode($mangsanpham);
	class SanPham 
	{
		public $id;
		public $tensp;
		public $giasp;
		public $hinhanhsp;
		public $motasp;
		public $idsanpham;
		function __construct($id,$tensp,$giasp,$hinhanhsp,$motasp,$idsanpham)
		{
			$this->id=$id;
			$this->tensp=$tensp;
			$this->giasp=$giasp;
			$this->hinhanhsp=$hinhanhsp;
			$this->motasp=$motasp;
			$this->idsanpham=$idsanpham;
		}
	}


?>