<?php
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();
    $response["admins"] = array();

	if($con){
		// Mysql select query
	    $result = mysqli_query($con, "SELECT admin_name, admin_email, admin_contact FROM admins");
	     
	    while($row = mysqli_fetch_array($result)){
	        // temporary array
	        $tmp = array();
	        $tmp["admin_name"] = $row["admin_name"];
	        $tmp["admin_email"] = $row["admin_email"];
	        $tmp["admin_contact"] = $row["admin_contact"];
	         
	        // push semesters to final json array
	        array_push($response["admins"], $tmp);
	    }
	     
	    // keeping response header to json
	    header('Content-Type: application/json');
	     
	    // echoing json result
	    echo json_encode($response);
	}
	else{
		echo "Failed to connect with database.";
	}


	

	
	//echo json_encode($response);


?>