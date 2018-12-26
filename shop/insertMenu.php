<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (
    isset($_POST['name'])&&
    isset($_POST['link'])) {
		
		
		
	$name = $_POST['name'];
	$link =$_POST['link'];	
	$result=$db->insertMenu($name,$link);
	
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