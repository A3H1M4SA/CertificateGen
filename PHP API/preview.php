<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');
require_once "dbconnect.php";

function calculateX($fontSize, $fontPath, $text, $imageWidth, $offsetX = 0) {
    $textBoundingBox = imagettfbbox($fontSize, 0, $fontPath, $text);
    $textWidth = $textBoundingBox[2] - $textBoundingBox[0];
    // Applys the offset here, positive value moves to the right, negative to the left
    return ($imageWidth - $textWidth) / 2 + $offsetX;
}

// Function to calculate the Y coordinate with vertical offset
function calculateY($fontSize, $fontPath, $text, $imageHeight, $offsetY = 0) {
    $textBoundingBox = imagettfbbox($fontSize, 0, $fontPath, $text);
    // Calculate the height of the text to position it at the baseline
    $textHeight = $textBoundingBox[1] - $textBoundingBox[7];
    // Apply the offset here, positive value moves down, negative moves up
    return ($imageHeight - $textHeight) / 2 + $offsetY;
}

// Get the parameters from the URL or GET Parameters
$name = isset($_GET["name"]) ? $_GET["name"] : "Default Name";
$signedBy = isset($_GET["signedBy"]) ? $_GET["signedBy"] : "Default Signee";
$style = isset($_GET["style"]) ? $_GET["style"] : "Style 1";
$company = isset($_GET["company"]) ? $_GET["company"] : "Default Company";
$dateOfIssue = isset($_GET["dateOfIssue"]) ? $_GET["dateOfIssue"] : date("F j, Y");
$cert_name = isset($_GET["certname"]) ? $_GET["certname"] : "Default Certificate";



// Path to the certificate template and font
$query = "SELECT * FROM `certificate_db` WHERE certificate_name='$cert_name'";
$query_run = mysqli_query($link, $query);
while ($rows = mysqli_fetch_array($query_run)) 
{
    $imagePath = $rows['certificate_path'].".png"; // Certifcate Image Path 
    $fontPath = $rows['primaryfont']; // General Font Path
    $signedByFontPath = $rows['primaryfont']; // Path to the font for 'Signed By
}



// Checks if the image file exists and is readable
if (!file_exists($imagePath) || !is_readable($imagePath)) {
    die('The image file does not exist or is not readable.');
}

$image = imagecreatefrompng($imagePath);
if (!$image) {
    die('Failed to create image from file.');
}

// Gets image dimensions
$imageWidth = imagesx($image);
$imageHeight = imagesy($image);


// Sets the text color (black)
$query = "SELECT * FROM `certificate_db` WHERE certificate_name='$cert_name'";
$query_run = mysqli_query($link, $query);
while ($rows = mysqli_fetch_array($query_run)) 
{

    // Gets the co-ordinates for each from the DB
    $name_cords = explode(",", trim($rows['name_loc'], "()")) ;
    $signed_cords = explode(",", trim($rows['signedby_loc'], "()")) ;
    $company_cords = explode(",", trim($rows['company_loc'], "()")) ;
    $date_cords = explode(",", trim($rows['date_loc'], "()")) ;

    $name_rgb = explode(",", trim($rows['name_textcolor'], "()")) ;
    $signedby_rgb = explode(",", trim($rows['signedby_textcolor'], "()")) ;
    $company_rgb = explode(",", trim($rows['company_textcolor'], "()")) ;
    $date_rgb = explode(",", trim($rows['date_textcolor'], "()")) ;

    //Gets the Fontsize for each Values from the DB
    $fontSize_Name = $rows['name_fontsize'];
    $fontSize_SignedBy = $rows['signedby_fontsize'];
    $fontSize_Company = $rows['company_fontsize'];
    $fontSize_Date = $rows['date_fontsize'];


    if($rows['name_loc'] != 'NIL')
    {
        // Sets text name and color
        $nameText = $name;
        $textColour = imagecolorallocate($image,  $name_rgb[0], $name_rgb[1], $name_rgb[2]);

        // Sets x/y desired offsets here
        $offsetX = $name_cords[0]; // Horizontal adjustment (left/right)
        $offsetY = $name_cords[1]; // Vertical adjustment (up/down)
        $x = calculateX($fontSize_Name, $fontPath, $nameText, $imageWidth, $offsetX);
        $y = calculateY($fontSize_Name, $fontPath, $nameText, $imageHeight, $offsetY);

        imagettftext($image, $fontSize_Name, 0, $x, $y, $textColour, $fontPath, $nameText);
    }

    if($rows['signedby_loc'] != 'NIL')
    {
        // Signed by
        $textColour_SignedBy = imagecolorallocate($image,  $signedby_rgb[0], $signedby_rgb[1], $signedby_rgb[2]);
        $signedByText = $signedBy;
        $offsetXSignedBy = $signed_cords[0]; // Horizontal adjustment for Signed By
        $offsetYSignedBy = $signed_cords[1]; // Vertical adjustment for Signed By
        $xSignedBy = calculateX($fontSize_SignedBy, $signedByFontPath, $signedByText, $imageWidth, $offsetXSignedBy);
        $ySignedBy = calculateY($fontSize_SignedBy, $signedByFontPath, $signedByText, $imageHeight, $offsetYSignedBy);
        imagettftext($image, $fontSize_SignedBy, 0, $xSignedBy, $ySignedBy, $textColour_SignedBy, $signedByFontPath, $signedByText);
    }

    if($rows['company_loc'] != 'NIL')
    {

        // Company
        $textColour_Company = imagecolorallocate($image,  $company_rgb[0], $company_rgb[1], $company_rgb[2]);
        $companyByText = $company;
        $offsetXSignedBy = $company_cords[0]; // Horizontal adjustment
        $offsetYSignedBy = $company_cords[1]; // Vertical adjustment
        $xSignedBy = calculateX($fontSize_Company, $signedByFontPath, $companyByText, $imageWidth, $offsetXSignedBy);
        $ySignedBy = calculateY($fontSize_Company, $signedByFontPath, $companyByText, $imageHeight, $offsetYSignedBy);
        imagettftext($image, $fontSize_Company, 0, $xSignedBy, $ySignedBy, $textColour_Company, $signedByFontPath, $companyByText);
    }

    
    if($rows['date_loc'] != "NIL")
    {
        // Date
        $textColour_Date = imagecolorallocate($image,  $date_rgb[0], $date_rgb[1], $date_rgb[2]);
        $datetext = $dateOfIssue;
        $offsetXSignedBy = $date_cords[0]; // Horizontal adjustment
        $offsetYSignedBy = $date_cords[1]; // Vertical adjustment
        $xSignedBy = calculateX($fontSize_Date, $signedByFontPath, $datetext, $imageWidth, $offsetXSignedBy);
        $ySignedBy = calculateY($fontSize_Date, $signedByFontPath, $datetext, $imageHeight, $offsetYSignedBy);
        imagettftext($image, $fontSize_Date, 0, $xSignedBy, $ySignedBy, $textColour_Date, $signedByFontPath, $datetext);
    }


    //Update API for Certificate
    $updatecertcount = "UPDATE certificate_db SET certificate_used=certificate_used+1 WHERE certificate_name='$cert_name'";
    $run_1 = mysqli_query($link, $updatecertcount);


}

// Code to Preview Certificate Whilst testing API


// Set the content-type
header('Content-Type: image/png');

// Output the image
imagepng($image);

// Free up memory
imagedestroy($image);

unlink($tempImage);


?>


// Appreciation Certificate
// Fields : Name, Sign, Company

// Participation Certificate
// Fields : Name, Sign, Company

// Best Employee Certificate
// Fields : Name, Sign, Company, Date