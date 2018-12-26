<?php
require_once 'db_function.php';
$db=new db_function();
$banners=$db->getAllBanner();
echo json_encode($banners);
?>