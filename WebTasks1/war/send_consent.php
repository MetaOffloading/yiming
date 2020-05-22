<?php
$to = $_GET['to'];
$subject = "UCL Mechanical Turk Experiment: Consent Form";
$headers = "From: Sam Gilbert <sam.gilbert@ucl.ac.uk>";

$body = "This is a copy of the consent form that you have requested. There is no need to fill in this information now, it is simply being provided so that you have a record of the questions you have been asked.

Title of project: Online response time studies of attention and memory

This study has been approved by the UCL Research Ethics Committee as Project ID Number: 1584/003

Thank you for your interest in taking part in this research. If you have any questions arising from the Information Page that you have already seen, please contact the experimenter before you decide whether to continue. You can go back to the Information Page by clicking the 'Go back to information page' button below.

Please confirm the following:

I have read the information page

I have had the opportunity to contact the researcher to ask questions and discuss the study

I have received satisfactory answers to my questions or have been advised of an individual to contact for answers to pertinent questions about the research and my rights as a participant

I understand that I am free to withdraw at any time, without giving a reason, and without incurring any penalty

I consent to the processing of my personal information for the purposes explained to me. I understand that such information will be handled in accordance with all applicable data protection legislation

I agree that my anonymised research data may be used by others for future research. I understand that no one will be able to identify me when this data is shared

Please enter your age in years:

Are you male/female/other?
";


if (mail($to, $subject, $body,$headers)) {} else {
echo("<p>Message delivery failed...</p>");
}
?>