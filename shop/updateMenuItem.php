<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (isset($_POST['id'])
	&&isset($_POST['name'])
    &&isset($_POST['link'])
	&&isset($_POST['price'])

     ){
    $id=$_POST['id'];
	$name=$_POST['name'];
	$link=$_POST['link'];
	$price=$_POST['price'];

    if ($db->updateMenuItem($id,$name,$link,$price)){
        $response["exists"]=true;
        echo json_encode($response);
    }else{
        $response["exists"]=false;
        echo json_encode($response);
    }

}else{
    $response["error_msg"]="Required parameter (id) is missing";
    echo json_encode($response);
}
?>