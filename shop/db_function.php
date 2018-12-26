<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
class  db_function{
    private  $conn;

    function  __construct()
    {

        require_once  'db_connection.php';

        $db=new db_connection();

        $this->conn=$db->connect();
    }

    function __destruct(){

    }

    function  checkExistsUser($phone){

        $stmt=$this->conn->prepare("SELECT * FROM user WHERE Phone=?");
        $stmt->bind_param("s",$phone);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows>0){
            $stmt->close();
            return true;
        }else{
            $stmt->close();
            return false;
        }
    }

    public function  registerNewUser($phone,$name,$birthday,$address){

        $stmt=$this->conn->prepare("INSERT INTO user (Phone,Name,Birthday,Address) VALUES (?,?,?,?)");
        $stmt->bind_param("ssss",$phone,$name,$birthday,$address);
        $result=$stmt->execute();
        $stmt->close();

        if ($result){
            $stmt=$this->conn->prepare("SELECT * FROM user WHERE Phone =?");
            $stmt->bind_param("s",$phone);
            $stmt->execute();
            $user=$stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        }else{
            return false;
        }

    }
	



    public function  insertOrderDetails($id,$orderName, $orderPrice, $orderSize, $orderQuantity) {

        //$stmt=$this->conn->prepare("INSERT INTO order (OrderStatus, OrderName, OrderPrice, OrderSize, OrderQuantity) VALUES (0, ?, ?, ?, ?)");
        
        //$stmt->close();


        $stmt = $this->conn->prepare("INSERT INTO `order`(OrderId,OrderStatus, OrderName, OrderPrice, OrderSize,OrderQuantity) VALUES (?,0, ?, ?, ?, ?)");
        if (!$stmt) {
            echo "false";
        } else {
            echo "true";
            
            $stmt->bind_param("sssss",$id, $orderName, $orderPrice, $orderSize, $orderQuantity);
            $result=$stmt->execute();
        }

        if ($result) {
            return false;
        } else {
            return false;
        }
    }
	
	public function  insertCustomerInfo($customerName, $customerAddress, $cutomerNumber, $customerImage,$menuId) {

        //$stmt=$this->conn->prepare("INSERT INTO order (OrderStatus, OrderName, OrderPrice, OrderSize, OrderQuantity) VALUES (0, ?, ?, ?, ?)");
        
        //$stmt->close();


        $stmt = $this->conn->prepare("INSERT INTO `Ordercustomerinfo`(CustomerName,CustomerAddress,CustomerNumber,CustomerImage,MenuId) VALUES (?, ?, ?, ?, ?)");
        if (!$stmt) {
            echo "false";
        } else {
            echo "true";
            
            $stmt->bind_param("sssss", $customerName, $customerAddress, $cutomerNumber, $customerImage,$menuId);
            $result=$stmt->execute();
        }

        if ($result) {
            return true;
        } else {
            return false;
        }
    }

	

    public function getUserInformation($phone){
        $stmt=$this->conn->prepare("SELECT * FROM user WHERE Phone =?");
        $stmt->bind_param("s",$phone);
        if ($stmt->execute()){
            $user=$stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        }else{
            return null;
        }

    }


    /**
     * @return array
     */
    public function getAllBanner(){
        $result=$this->conn->query("SELECT * FROM banner ORDER BY ID LIMIT 4");


       $banners=array();

       while ($item = $result->fetch_assoc())
           $banners[]=$item;
        return $banners;
    }
	
	public function getMenu(){
       $result=$this->conn->query("SELECT * FROM menu");
       $menus=array();
	   
	    while ($item = $result->fetch_assoc())
           $menus[]=$item;
        return $menus;
	   
    }
	
	
	
	
	public function getMenuByMenuId($menuId){
		
		$query ="SELECT * FROM menutype WHERE Menuid='".$menuId."'";
		$result=$this->conn->query($query);
		
		$menudata=array();
		
		
		while ($items=$result->fetch_assoc()){
			$menudata[]=$items;
		}
			
		return $menudata;
	  
	   
    }
	
	
	public function getImage($phone,$fileName){
		
	   return $result= $this->conn->query("UPDATE user SET userImage='$fileName' WHERE Phone='$phone'");
	   
    }
	
	public function getAllForSerchMenu(){
		$result=$this->conn->query("SELECT * FROM menutype WHERE 1") or die($this->conn->error);
		
		$Item=array();
		while($item=$result->fetch_assoc()){
			$Item[]=$item;
		}
		return $Item;

	}
	
	function  checkAdmminIsAvilable($email,$password){

        $stmt=$this->conn->prepare("SELECT * FROM adminmember WHERE Email=? AND Password=?");
        $stmt->bind_param("ss",$email,$password);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows!=null){
            $stmt->close();
            return true;
        }else{
            $stmt->close();
            return false;
        }
    }
	
	public function  insertMenu($name, $link) {

        $stmt = $this->conn->prepare("INSERT INTO menu(Name,Link) VALUES (?,?)");
        
        $stmt->bind_param("ss", $name, $link);
        $result=$stmt->execute();
		$stmt->close();
		
		if ($result) {
            return true;
        } else {
            return false;
        }

    }
	
	public function deleteMenu($id){
		$stmt=$this->conn->prepare("DELETE FROM menu WHERE ID=?");
		$stmt->bind_param("s",$id);
		$result=$stmt->execute();
		$stmt->close();
		if($result){
			return true;
		}else{
			return false;
		}
	}
	
/*UPDATE Customers
SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
WHERE CustomerID = 1;*/
	
	
	public function updateMenu($id,$name,$link){
		$stmt=$this->conn->prepare("UPDATE menu SET Name = ? ,Link = ? WHERE ID=?");
		$stmt->bind_param("sss",$name,$link,$id);
		$result=$stmt->execute();
		$stmt->close();
		if($result){
			return true;
		}else{
			return false;
		}
	}
	
	
	public function updateMenuItem($id,$name,$link,$price){
		$stmt=$this->conn->prepare("UPDATE menutype SET Name = ? ,Link = ? , Price = ? WHERE ID=?");
		$stmt->bind_param("ssss",$name,$link,$price,$id);
		$result=$stmt->execute();
		$stmt->close();
		if($result){
			return true;
		}else{
			return false;
		}
	}
	
	public function  insertDataIntoTheMenuType($link,$name,$price,$menuId){
		$stmt=$this->conn->prepare("INSERT INTO menutype(Name,Link,Price,Menuid) VALUES(?,?,?,?)");
		$stmt->bind_param("ssss", $name,$link,$price,$menuId);
	    $result=$stmt->execute();
		$stmt->close();
		
		if ($result) {
            return true;
        } else {
            return false;
        }
		
	}
	
	public function deleteMenuItem($id){
		$stmt=$this->conn->prepare("DELETE FROM menutype WHERE ID=?");
		$stmt->bind_param("s",$id);
		$result=$stmt->execute();
		$stmt->close();
		if($result){
			return true;
		}else{
			return false;
		}
	}
	
	public function getAllOrderInfo(){
		
		$result=$this->conn->query("SELECT * FROM `order`");
		
		$Item=array();
		
		if($result){
		while($item= $result->fetch_assoc())
			$Item[]=$item;
		}else{
			$Item=["Fuck"];
		}
		return $Item;
	}
	public function getAllCoustomerInfo($id){
		
		$qury=$this->conn->query("SELECT * FROM `ordercustomerinfo` WHERE MenuId ='".$id."'");
		//$stmt->bind_param("s",$id);
		//$stmt->bind_param("s",$id);
		//$result=$stmt->execute();
		//$result=$stmt->execute(); 
		//$Item=array();
		
		//while($item=$stmt->fetch_assoc())
			//$Item[]=$item;
		
		return $qury->fetch_assoc();
	}
	
	
	
}

?>