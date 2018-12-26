<?php
 require_once 'db_function.php';
 $db=new db_function();
 
 if(isset($_FILES["uploaded_file"]["name"])){
	 if(isset($_POST["phone"])){
		 $phone=$_POST["phone"];
	     $name=$_FILES["uploaded_file"]["name"];
		 $tmp_name=$_FILES["uploaded_file"]["tmp_name"];
		 $error=$_FILES["uploaded_file"]["error"];
		 
		 if(!empty($name)){
			 $location='./user_image/';
			 if(!is_dir($location)){
				 mkdir($location);
			 }
			 if(move_uploaded_file($tmp_name,$location . $name)){
				  $result=$db->getImage($phone,$name);
			 }
			 if($result){
				 echo json_encode("Uploaded..");
			 }else{
				 echo json_encode("Failed to upload file");
			 }
			 
		 }
		 
		 
	 }else{
		 echo json_encode("Missing phone field");
	 }
	 
 }else{
	 echo json_encode("Please input file");
 }
 
?>
