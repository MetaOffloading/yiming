package com.sam.webtasks.client;

import com.sam.webtasks.basictools.Counterbalance;

public class Instructions {

	public static String Get(int index) {
		String i="";
		 
		switch(index) {
		case 0:
			i="Factors 1 and 2 have been set according to the participant code. They are set to levels " + Counterbalance.getFactorLevel("factor1")
			+ " and " + Counterbalance.getFactorLevel("factor2") + " respectively.<br><br>Factor 3 is set to a random level between 0 - 49. It "
			+ "has been set to " + Counterbalance.getFactorLevel("factor3") +  ".<br><br>Factor 4 has been fixed at level two ("
			+ Counterbalance.getFactorLevel("factor4") + ") and will not vary.";
			break;
		}

		return(i);	
	}
	
	public static String InfoText() {
        return ("We would like to invite you to participate in this research project. "
                + "You should only participate if you want to; choosing not to take part "
                + "will not disadvantage you in any way. Before you decide whether you "
                + "want to take part, please read the following information carefully and "
                + "discuss it with others if you wish. Ask us if there is anything that "
                + "is not clear or you would like more information.<br><br>"
                + "We are recruiting volunteers from the Amazon Mechanical Turk website to "
                + "take part in an experiment aiming to improve our understanding of human "
                + "attention and memory. You will perform some simple tasks using your web-browser, which will be "
                + "explained in full. "
                + "The experiment "
                + "will last approximately 40 minutes. There are no anticipated risks or "
                + "benefits associated with participation in this study.<br><br>"
                + "It is up to you to decide whether or not to take part. If you choose "
                + "not to participate, you won't incur any penalties or lose any "
                + "benefits to which you might have been entitled. However, if you do "
                + "decide to take part, you can print out this information sheet and "
                + "you will be asked to fill out a consent form on the next page. "
                + "Even after agreeing to take "
                + "part, you can still withdraw at any time and without giving a reason. "
                + "<br><br>All data will be collected and stored in accordance with the General "
                + "Data Protection Regulations 2018. Personal information is stored separately from test results, "
                + "and researchers on this project have no access to this data. Data "
                + "from this experiment may be made available to the research comunity, for example by posting "
                + "them on websites such as the Open Science Framework (<a href=\"https://osf.io/\">http://osf.io</a>). It "
                + "will not be possible to identify you from these data.<br><br>We aim to publish the results "
                + "of this project in scientific journals and book chapters. Copies of the results can either be obtained "
                + "directly from the scientific journals' websites or from us.<br><br>"
                + "Should you wish to raise a complaint, please contact the Principal Investigator of "
                + "this project, Dr Sam Gilbert (<a href=\"mailto:sam.gilbert@ucl.ac.uk\">sam.gilbert@ucl.ac.uk</a>). "
                + "However, if you feel your complaint has not been handled to your satisfaction, please be aware that you can also "
                + "contact the Chair of the UCL research Ethics Committee (<a href=\"ethics@ucl.ac.uk\">ethics@ucl.ac.uk</a>).");
                		
        
    }

}
