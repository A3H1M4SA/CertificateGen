<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');
require_once "dbconnect.php";

function calculateX($fontSize, $fontPath, $text, $imageWidth, $offsetX = 0) {
    $textBoundingBox = imagettfbbox($fontSize, 0, $fontPath, $text);
    $textWidth = $textBoundingBox[2] - $textBoundingBox[0];
    // Apply the offset here, positive value moves to the right, negative to the left
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
$style = isset($_GET["style"]) ? $_GET["style"] : "Style 1";
$company = isset($_GET["company"]) ? $_GET["company"] : "Default Company";
$signedBy = isset($_GET["signedBy"]) ? $_GET["signedBy"] : "Default Signee";
$dateOfIssue = isset($_GET["dateOfIssue"]) ? $_GET["dateOfIssue"] : date("F j, Y");
$cert_name = isset($_GET["certname"]) ? $_GET["certname"] : "Default Certificate";



// Path to the certificate template and font
$imagePath = 'assets/certificate_of_appreciation/appreciation_1.png'; // Make sure this path is correct
$fontPath = 'fonts/ArianaVioleta.ttf'; // Make sure this path is correct and the file is readable
$signedByFontPath = 'fonts/signedByFont.ttf'; // Path to the font for 'Signed By'


// Check if the image file exists and is readable
if (!file_exists($imagePath) || !is_readable($imagePath)) {
    die('The image file does not exist or is not readable.');
}

$image = imagecreatefrompng($imagePath);
if (!$image) {
    die('Failed to create image from file.');
}

// Get image dimensions
$imageWidth = imagesx($image);
$imageHeight = imagesy($image);

// Define the font size
$fontSize = 100; // Adjust the font size as needed

// Set the text color (black)
$query = "SELECT * FROM `certificate_db` WHERE certificate_name='$cert_name'";
$query_run = mysqli_query($link, $query);
while ($rows = mysqli_fetch_array($query_run)) 
{
    $textColour = imagecolorallocate($image, 160,82,45);

    // Gets the co-ordinates for the names from the DB
    $name_cords = explode(",", trim($rows['name_loc'], "()")) ;
    // Name text
    $nameText = $name;
    // Set your desired offsets here
    $offsetX = $name_cords[0]; // Horizontal adjustment (left/right)
    $offsetY = $name_cords[1]; // Vertical adjustment (up/down)
    $x = calculateX($fontSize, $fontPath, $nameText, $imageWidth, $offsetX);
    $y = calculateY($fontSize, $fontPath, $nameText, $imageHeight, $offsetY);

    imagettftext($image, $fontSize, 0, $x, $y, $textColour, $fontPath, $nameText);

    // // Signed by
    $signedByFontPath = 'fonts/ArianaVioleta.ttf'; // Path to the different font for 'Signed By'
    $signedByText = $signedBy;
    $offsetXSignedBy = 0; // Horizontal adjustment for Signed By
    $offsetYSignedBy = 1150; // Vertical adjustment for Signed By
    $xSignedBy = calculateX($fontSize, $signedByFontPath, $signedByText, $imageWidth, $offsetXSignedBy);
    $ySignedBy = calculateY($fontSize, $signedByFontPath, $signedByText, $imageHeight, $offsetYSignedBy);
    imagettftext($image, $fontSize, 0, $xSignedBy, $ySignedBy, $textColour, $signedByFontPath, $signedByText);
}

// // Date of Issue
// $dateOfIssueText = "Date of Issue: " . $dateOfIssue;
// imagettftext($image, 24, 0, calculateX(24, $fontPath, $dateOfIssueText, $image), calculateY($image, 0.6), $textColour, $fontPath, $dateOfIssueText);

// Set the content-type
header('Content-Type: image/png');

// Output the image
imagepng($image);

// Free up memory
imagedestroy($image);
?>