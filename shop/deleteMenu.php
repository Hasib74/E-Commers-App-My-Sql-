<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (isset($_POST['id'])){
    $id=$_POST['id'];

    if ($db->deleteMenu($id)){
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