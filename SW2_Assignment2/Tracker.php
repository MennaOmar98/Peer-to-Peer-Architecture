<?php

    $Server_name = "localhost";
    $Server_host = "root";
    $Server_password = "";
    $Database_name = "tracker_database";
    $conn = mysqli_connect($Server_name,$Server_host,$Server_password,$Database_name);
    
    if($_POST){
        if(isset($_POST['ip']) && isset($_POST['port'])){
            if(filter_var($_POST['ip'],FILTER_VALIDATE_IP)){
                $insert_text = "INSERT INTO `PeersDetails`(`IP`, `Port`) VALUES ('{$_POST['ip']}','{$_POST['port']}');";
                $insert_query_return = mysqli_query($conn,$insert_text);
                
                $select_text = "SELECT `IP`, `Port` FROM `PeersDetails` WHERE IP != '{$_POST['ip']}';";
                $select_query_return = mysqli_query($conn,$select_text);
                $array = array();
                while($row = $select_query_return->fetch_assoc()){
                    array_push($array, array(
                        "ip" => $row["IP"],
                        "port" => $row['Port'],
                    ));
                }
                print_r(json_encode($array));
            }
        }
    }
    
?>