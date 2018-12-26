<?php
require_once 'db_function.php';
$db=new db_function();

$Items=$db->getAllForSerchMenu();

echo json_encode($Items);


?>