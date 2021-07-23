<?php
	/**
	* 
	*/
	class operation
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