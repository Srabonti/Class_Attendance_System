<?php
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();
    $response["semesters"] = array();

	if($con){
		// Mysql select query
	    $result = mysqli_query($con, "SELECT * FROM semesters ORDER BY semester_id ASC");
	     
	    while($row = mysqli_fetch_array($result)){
	        // temporary array
	        $tmp = array();
	        $tmp["semester_id"] = $row["semester_id"];
	        $tmp["semester_name"] = $row["semester_name"];
	         
	        // push semesters to final json array
	        array_push($response["semesters"], $tmp);
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