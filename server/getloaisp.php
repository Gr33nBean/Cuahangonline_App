<?php 
	include "connect.php";
	
	class Loaisp
	{
		public $id;
		public $tenloaisp;
		public $hinhanhloaisp;
		public function __construct($id1,$tenloaisp1,$hinhanhloaisp1)
		{
			$this->id = $id1;
			$this->tenloaisp = $tenloaisp1;
			$this->hinhanhloaisp = $hinhanhloaisp1;
		} 
	}

	$data = mysqli_query($conn,"SELECT * FROM `loaisanpham`;");
	$mangloaisp = array();
	foreach ($data as $value) {
		$temp = new Loaisp($value['id'],$value['tenloaisanpham'],$value['hinhanhloaisanpham']);
		array_push($mangloaisp,$temp);
	}
	echo json_encode($mangloaisp);
?>