<?php
$to = $_GET['to'];
$subject = "UCL Mechanical Turk Experiment: Information Sheet";
$headers = "From: Sam Gilbert <sam.gilbert@ucl.ac.uk>";

$body = "Title of project: Online response time studies of attention and memory

This study has been approved by the UCL Research Ethics Committee as Project ID Number: 1584/003

Name, address and contact details of investigators:
Dr Sam Gilbert
Institute of Cognitive Neuroscience
17 Queen Square
London WC1N 3AR

sam.gilbert@ucl.ac.uk

We would like to invite you to participate in this research project. You should only participate if you want to; choosing not to take part will not disadvantage you in any way. Before you decide whether you want to take part, please read the following information carefully and discuss it with others if you wish. Ask us if there is anything that is not clear or you would like more information.

We are recruiting volunteers from the Amazon Mechanical Turk website to take part in an experiment aiming to improve our understanding of human attention and memory. You will see various objects on the screen like coloured, numbered circles, and you will be asked to move them with your computer mouse. Sometimes you will be asked to remember particular numbers and move the corresponding circle in a particular direction. You will be asked how confident you are in your ability to solve the task. The experiment will last approximately 45 minutes and you will receive a payment of $2 plus an additional bonus via the Amazon Mechanical Turk payment system. There are no anticipated risks or benefits associated with participation in this study.

It is up to you to decide whether or not to take part. If you choose not to participate, you won't incur any penalties or lose any benefits to which you might have been entitled. However, if you do decide to take part, you can print out this information sheet and you will be asked to fill out a consent form on the next page. Even after agreeing to take part, you can still withdraw at any time and without giving a reason. If you withdraw before the end of the experiment, we will not retain your data and it will not be analysed.

All data will be collected and stored in accordance with the General Data Protection Regulations 2018. Personal information is stored separately from test results, and researchers on this project have no access to this data. Your personal information such as name and email address is held by Amazon Mechanical Turk but the researchers on this project have no acccess to this. Data from this experiment may be made available to the research community, for example by posting them on websites such as the Open Science Framework (http://osf.io). It will not be possible to identify you from these data.

We aim to publish the results of this project in scientific journals and book chapters. Copies of the results can either be obtained directly from the scientific journals' websites or from us.

Should you wish to raise a complaint, please contact the Principal Investigator of this project, Dr Sam Gilbert (sam.gilbert@ucl.ac.uk). However, if you feel your complaint has not been handled to your satisfaction, please be aware that you can also contact the Chair of the UCL Research Ethics Committee (ethics@ucl.ac.uk).";


if (mail($to, $subject, $body,$headers)) {} else {
echo("<p>Message delivery failed...</p>");
}
?>