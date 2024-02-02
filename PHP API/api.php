<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

// Function to calculate the X coordinate for center alignment
function calculateX($fontSize, $fontPath, $text, $imageWidth) {
    $textBoundingBox = imagettfbbox($fontSize, 0, $fontPath, $text);
    $textWidth = $textBoundingBox[2] - $textBoundingBox[0];
    return ($imageWidth - $textWidth) / 2;
}

// Function to calculate the Y coordinate based on the desired position
function calculateY($imageHeight, $desiredPosition) {
    return intval($imageHeight * $desiredPosition);
}

// Get the parameters from the URL or GET Parameters
$name = isset($_GET["name"]) ? $_GET["name"] : "Default Name";
$style = isset($_GET["style"]) ? $_GET["style"] : "Style 1";
$company = isset($_GET["company"]) ? $_GET["company"] : "Default Company";
$signedBy = isset($_GET["signedBy"]) ? $_GET["signedBy"] : "Default Signee";
$dateOfIssue = isset($_GET["dateOfIssue"]) ? $_GET["dateOfIssue"] : date("F j, Y");

// Path to the certificate template and font
$imagePath = 'assets/certificate_of_appreciation/1.png'; // Make sure this path is correct
$fontPath = 'fonts/font2.ttf'; // Make sure this path is correct and the file is readable

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
$textColour = imagecolorallocate($image, 0, 0, 0);

// Name text
$nameText = $name;
$x = calculateX($fontSize, $fontPath, $nameText, $imageWidth); // Use $imageWidth here
$y = calculateY($imageHeight, 0.5); // Adjust the decimal to set the vertical position
imagettftext($image, $fontSize, 0, $x, $y, $textColour, $fontPath, $nameText);

// // Signed by
// $signedByText = "Signed by: " . $signedBy;
// imagettftext($image, 24, 0, calculateX(24, $fontPath, $signedByText, $image), calculateY($image, 0.55), $textColour, $fontPath, $signedByText);

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
