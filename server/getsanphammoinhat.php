<?php 
	include "connect.php";
	class Sanphammoinhat
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
	$mangspmoinhat = array();
	$query = "SELECT * FROM `sanpham` ORDER BY id DESC LIMIT 6;";
	$data = mysqli_query($conn,$query);
	foreach ($data as $value) {
		$temp = new Sanphammoinhat($value['id'],$value['tensanpham'],$value['giasanpham'],$value['hinhanhsanpham'],$value['motasanpham'],$value['idsanpham']);
		array_push($mangspmoinhat,$temp);
	}
	echo json_encode($mangspmoinhat);
	

?>