<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (isset($_POST['email'])&&
    isset($_POST['password'])){

    $email=$_POST['email'];
    $password=$_POST['password'];
   

    if ($db->checkAdmminIsAvilable($email,$password)){
		
		
		$response["response"]="success";
		
		echo json_encode($response);
		
		
    }else{
       
		 $response["response"]="Error";
		
		echo json_encode($response);
    }
	

}else{
    $response["error_msg"]="Required parameter (email,password) is missing";
    echo json_encode($response);
}
?>