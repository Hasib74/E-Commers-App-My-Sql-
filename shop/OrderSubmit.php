<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (//isset($_POST['orderStatus'])&&
isset($_POST['id'])&&
    isset($_POST['orderName'])&&
    isset($_POST['orderPrice'])&&
    isset($_POST['orderSize'])&&
	isset($_POST['orderQuantity'])) {
			
	//$orderStatus = $_POST['orderStatus'];
	$id = $_POST['id'];
	$orderName = $_POST['orderName'];
	$orderPrice=$_POST['orderPrice'];
	$orderSize=$_POST['orderSize'];
	
	$orderQuantity=$_POST['orderQuantity'];



	//$result=$db->insertOrder($orderStatus, $price, $orderDetails, $comment, $address, $phone);
	$result=$db->insertOrderDetails($id,$orderName, $orderPrice, $orderSize, $orderQuantity);
    // if ($result) {
    //     echo json_encode("true");
    // } else {
    //     echo json_encode("false");
    // }
} else {
    $response["error_msg"]="Required parameter (orderDetails,phone,address,comment,price) is missing";
    echo json_encode($response);
}
?>