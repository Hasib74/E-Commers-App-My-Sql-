<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (isset($_POST['id'])){

    $id=$_POST['id'];


        /*$response["exists"]=false;
        echo json_encode($response);*/
        $response=$db->getAllCoustomerInfo($id);
    

        echo json_encode($response);
       

}else{
    $response["error_msg"]="Required parameter (phone) is missing";
    echo json_encode($response);
}
?>