<?php
require_once 'db_function.php';
$db=new db_function();

$response=array();
if (isset($_POST['phone'])&&
    isset($_POST['name'])&&
    isset($_POST['birthday'])&&
    isset($_POST['address'])){

    $phone=$_POST['phone'];
    $name=$_POST['name'];
    $birthday =$_POST['birthday'];
    $address=$_POST['address'];

    if ($db->checkExistsUser($phone)){
        $response["error_msg"]="User Already exists with ".$phone;
        echo json_encode($response);
    }else{
        /*$response["exists"]=false;
        echo json_encode($response);*/
        $user=$db->registerNewUser($phone,$name,$birthday,$address);
        if ($user){
            $response["phone"]=$user["Phone"];
            $response["name"]=$user["Name"];
            $response["birthday"]=$user["Birthday"];
            $response["address"]=$user["Address"];

            echo json_encode($response);
        }else{
            $response["error_msg"]="Unknown error occurred in registation";
            echo json_encode($response);
        }
    }

}else{
    $response["error_msg"]="Required parameter (phone,name,birthdate,address) is missing";
    echo json_encode($response);
}
?>