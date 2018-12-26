<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (isset($_POST['Menuid'])){

    $menuid=$_POST['Menuid'];


        /*$response["exists"]=false;
        echo json_encode($response);*/
        $menuFromMenuId=$db->getMenuByMenuId($menuid);
        
            echo json_encode($menuFromMenuId);
       


}else{
    $response["error_msg"]="Required parameter (Menuid) is missing";
    echo json_encode($response);
}
?>