<?php
	/**
	* 
	*/
	class DbOperations
	{
		private $con;

		function __construct()
		{
			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();
		}


		/*CRUD -> C -> CREATE*/

		public function createAdmin($admin_name, $admin_email, $admin_contact,  $admin_pass){
			if($this->isUserExist($admin_name, $admin_email, $admin_contact)){
				return 0;
			}else{
				$admin_pass = md5($admin_pass);
				$stmt = $this->con->prepare("INSERT INTO `admins` (`admin_id`, `admin_name`, `admin_email`, `admin_contact`, `admin_pass`) VALUES (NULL, ?, ?, ?, ?);");
				$stmt->bind_param("ssss", $admin_name, $admin_email, $admin_contact, $admin_pass);

				if($stmt->execute()){
					return 1;
				}
				else{
					return 2;
				}
				/*$sql = "INSERT INTO admins (admin_name, admin_email, admin_contact, admin_pass) VALUES ($admin_name, $admin_email, $admin_contact, $admin_pass)";

				if($this->con->query($sql) == TRUE){
					return 1;
				}
				else{
					return 2;
				}*/
			}
			
		}

		public function userLogin($admin_email, $admin_pass){
			$admin_pass = md5($admin_pass);
			$stmt = $this->con->prepare("SELECT admin_id FROM admins WHERE admin_email = ? AND admin_pass = ?");
			$stmt->bind_param("ss", $admin_email, $admin_pass);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}

		public function getUserByUserEmail($admin_email){
			$stmt = $this->con->prepare("SELECT * FROM admins WHERE admin_email = ?");
			$stmt->bind_param("s", $admin_email);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}


		private function isUserExist($admin_name, $admin_email, $admin_contact){
			$stmt = $this->con->prepare("SELECT admin_id FROM admins WHERE admin_name = ? OR admin_email = ? OR admin_contact = ?");
			$stmt->bind_param("sss", $admin_name, $admin_email, $admin_contact);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}

		/*public function insertSemester($semester_id, $semester_name){
			$stmt = $this->con->prepare("INSERT INTO semesters (semester_id, semester_name) VALUES (?, ?);");
			$stmt->bind_param("ss", $semester_id, $semester_name);

			if($stmt->execute()){
				return true;
			}
			else{
				return false;
			}
		}*/

		public function insertSemester($semester_id, $semester_name){
			if($this->isSemesterExist($semester_id)){
				return 0;
			}
			else{
				$stmt = $this->con->prepare("INSERT INTO semesters (semester_id, semester_name) VALUES (?, ?);");
				$stmt->bind_param("ss", $semester_id, $semester_name);

				if($stmt->execute()){
					return 1;
				}
				else{
					return 2;
				}
			}
		}

		private function isSemesterExist($semester_id){
			$stmt = $this->con->prepare("SELECT semester_name FROM semesters WHERE semester_id = ?");
			$stmt->bind_param("s", $semester_id);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}

		public function insertCourse($course_id, $course_title, $course_credit, $course_type, $total_class_no, $semester_id){
			$stmt = $this->con->prepare("INSERT INTO course_info (course_id, course_title, course_credit, course_type, total_class_no, semester_id) 
				VALUES (?, ?, ?, ?, ?, ?);");
			$stmt->bind_param("ssssss", $course_id, $course_title, $course_credit, $course_type, $total_class_no, $semester_id);

			if($stmt->execute()){
				return true;
			}
			else{
				return false;
			}
		}

		public function insertStudents($student_id, $student_name, $student_contact, $student_email, $student_address, $guardian_name, $guardian_contact, $relation, $semester_id){
			$stmt = $this->con->prepare("INSERT INTO student_info (student_id, student_name, student_contact, student_email, student_address, guardian_name, guardian_contact, relation, semester_id) 
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			$stmt->bind_param("sssssssss", $student_id, $student_name, $student_contact, $student_email, $student_address, $guardian_name, $guardian_contact, $relation, $semester_id);

			if($stmt->execute()){
				return true;
			}
			else{
				return false;
			}
		}

		public function giveAttendance($attendance_date, $attendance_state, $course_id, $semester_id, $student_id){
			$stmt = $this->con->prepare("INSERT INTO attendances (attendance_id, attendance_date, attendance_state, course_id, semester_id, student_id) 
				VALUES (NULL, ?, ?, ?, ?, ?);");
			$stmt->bind_param("sssss", $attendance_date, $attendance_state, $course_id, $semester_id, $student_id);

			if($stmt->execute()){
				return true;
			}
			else{
				return false;
			}
		}

		/*public function giveAttendance($attendance_date, $attendance_state, $course_id, $semester_id, $student_id){
			$stmt = "UPDATE student_info SET attendance_date = '$attendance_date', attendance_state = '$attendance_state' WHERE student_id = '$student_id' AND semester_id = '$semester_id' AND course_id = '$course_id';

			
			if($this->con->query($stmt){
				return true;
			}
			else{
				return false;
			}
		}*/

		public function insertInfo($attendance_date, $attendance_state, $course_id, $semester_id, $student_id){
			$sql1 = "UPDATE student_info SET attendance_date = '$attendance_date', attendance_state = '$attendance_state', course_id = '$course_id' WHERE semester_id = '$semester_id' AND student_id = '$student_id'";
			if($this->con->query($sql1) == true){
				return true;
			}
			else{
				return false;
			}
		}

		/*public function attendanceS($attendance_state, $student_id){

			//$sql = "REPLACE INTO student_info (attendance_state) VALUES($attendance_state') WHERE student_id='$student_id' and course_id='$course_id'";
			$sql = "UPDATE student_info SET `attendance_state`='$attendance_state' WHERE student_id = '$student_id'";
			if($this->con->query($sql) == true){
		    	return true;
		    }
		    else{
		    	return false;
		    }

		}*/

		public function getAttendanceRecords($course_id){
			$sl = $this->con->prepare("SELECT student_id, student_name, attendance_state FROM student_info WHERE course_id = ?");
			$sl->bind_param("s", $course_id);
			$sl->execute();
			$sl->store_result();

			return $sl->num_rows > 0;

		}

		public function saveAttendanceRecord($student_id, $student_name, $attendance_state, $attendance_date, $course_id, $semester_id){
			$stmt = $this->con->prepare("INSERT INTO attendancerecords (record_id, student_id, student_name, attendance_state, attendance_date, course_id, semester_id) 
				VALUES (NULL, ?, ?, ?, ?, ?, ?);");
			$stmt->bind_param("ssssss", $student_id, $student_name, $attendance_state, $attendance_date, $course_id, $semester_id);

			if($stmt->execute()){
				return true;
			}
			else{
				return false;
			}
		}



	}
?>