<?php
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();


	if($con->connect_error){
		echo "Failed to connect with database: ".$con->connect_error;
		$response['error'] = true;
		$response['message'] = "Failed to connect to the database!";
		
	}
	else{
		// Mysql select query
	    $sql = "DELETE * FROM semesters";

	    if($con->query($sql) == true){
	    	$response['error'] = false;
			$response['message'] = "Semester records are deleted successfully!";
	    }
	    else{
	    	$response['error'] = true;
			$response['message'] = "Some error occured in deleting semester records!";
	    }
	    
	}

	// echoing json result
	echo json_encode($response);


?>