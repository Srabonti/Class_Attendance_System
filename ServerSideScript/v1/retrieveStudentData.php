<?php
	include_once "../includes/Constants.php";

	//$db = new DbConnect();
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	 // array for json response
    $response = array();
    $response["students"] = array();

	if($con){
		// Mysql select query
	    $result = mysqli_query($con, "SELECT student_id, student_name, guardian_contact FROM student_info");
	     
	    while($row = mysqli_fetch_array($result)){
	        // temporary array
	        $tmp = array();
	        $tmp["student_id"] = $row["student_id"];
	        $tmp["student_name"] = $row["student_name"];
	        $tmp["guardian_contact"] = $row["guardian_contact"];
	         
	        // push semesters to final json array
	        array_push($response["students"], $tmp);
	    }
	     
	    // keeping response header to json
	    header('Content-Type: application/json');
	     
	    // echoing json result
	    echo json_encode($response);
	}
	else{
		echo "Failed to connect with database.";
	}


?>