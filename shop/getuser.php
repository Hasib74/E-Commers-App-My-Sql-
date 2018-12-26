<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (isset($_POST['phone'])){

    $phone=$_POST['phone'];


        /*$response["exists"]=false;
        echo json_encode($response);*/
        $user=$db->getUserInformation($phone);
        if ($user){
            $response["phone"]=$user["Phone"];
            $response["name"]=$user["Name"];
            $response["birthday"]=$user["Birthday"];
            $response["address"]=$user["Address"];
			$response["userImage"]=$user["userImage"];

            echo json_encode($response);
        }else{
            $response["error_msg"]="User Not Exists";
            echo json_encode($response);
        }


}else{
    $response["error_msg"]="Required parameter (phone) is missing";
    echo json_encode($response);
}
?>