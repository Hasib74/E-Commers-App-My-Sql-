<?php
 require_once 'db_function.php';
 $db=new db_function();
 
 if(isset($_FILES["uploaded_file"]["name"])){
	 
		
	     $name=$_FILES["uploaded_file"]["name"];
		 $tmp_name=$_FILES["uploaded_file"]["tmp_name"];
		 $error=$_FILES["uploaded_file"]["error"];
		 
		 if(!empty($name)){
			 $location='./user_image/';
			 if(!is_dir($location)){
				 mkdir($location);
			 }
			 if(move_uploaded_file($tmp_name,$location . $name)){
				  //$result=$db->getImage($phone,$name);
				  echo json_encode($name);
			 }else{
				 
				 echo json_encode("Upload failed ! Move location error");
			 }
			 
			 
		 }
		 
		 
	 
	 
	 
 }else{
	 echo json_encode("Please input file");
 }
 
?>
