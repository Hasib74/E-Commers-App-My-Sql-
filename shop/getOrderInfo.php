<?php
require_once 'db_function.php';
$db=new db_function();

$menuss=$db->getAllOrderInfo();
	
	echo json_encode($menuss);

?>