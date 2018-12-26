<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (
    isset($_POST['link'])&&
    isset($_POST['name'])&&
	isset($_POST['price'])&&
	isset($_POST['menuId'])) {
		
		
		
	
	$link =$_POST['link'];	
	$name = $_POST['name'];
	$price = $_POST['price'];
	$menuId = $_POST['menuId'];
	
	$result=$db->insertDataIntoTheMenuType($link,$name,$price,$menuId);
	
	if($result){
		$response["insert"]="Insert";
		echo json_encode($response);
	}else{
		$response["insert"]="faile";
		echo json_encode($response);
	}
    
} else {
    $response["error_msg"]="Required parameter (name,link) is missing";
    echo json_encode($response);
}
?>