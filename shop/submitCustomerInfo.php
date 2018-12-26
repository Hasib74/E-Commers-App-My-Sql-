<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (//isset($_POST['orderStatus'])&&
    isset($_POST['customerName'])&&
    isset($_POST['customerAddress'])&&
    isset($_POST['cutomerNumber'])&&
	isset($_POST['customerImage'])&&
	isset($_POST['menuId'])
	) {
			
	//$orderStatus = $_POST['orderStatus'];
	$customerName = $_POST['customerName'];
	$customerAddress=$_POST['customerAddress'];
	$cutomerNumber=$_POST['cutomerNumber'];
	$customerImage=$_POST['customerImage'];
	
	$menuId=$_POST['menuId'];



	//$result=$db->insertOrder($orderStatus, $price, $orderDetails, $comment, $address, $phone);
	$result=$db->insertCustomerInfo($customerName, $customerAddress, $cutomerNumber, $customerImage,$menuId);
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