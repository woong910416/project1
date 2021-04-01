package com.project1;

public class ThreadSungWoong implements Runnable {

	@Override
	public void run() {

		try {
			System.out.println(
					"           ####                              ###  #  ##                                    ######                                     ");
			Thread.sleep(500);
			System.out.println(
					"          ##  #                               ##  #  #                                      ##  #                                      ");
			
			Thread.sleep(450);
			System.out.println(
					"          ###   ### ##  #####    #####        ## ### #   ####   ####  #####    #####        ##    ## ## #####  ### #  ###   ####  #### ");
			
			Thread.sleep(400);
			System.out.println(
					"           ###   ## ##   ## ##  ## ##         ## ### #  ## ### ## ###  ## ##  ## ##         ####   ###   ## ##  #### ## ## ###   ###   ");
			
			Thread.sleep(350);
			System.out.println(
					"            ###  ## ##   ## ##  ## #           ##  ##   ##  ## ##  ##  ## ##  ## #          ##      #    ## ##  ##   #####  ###   ###  ");
			
			Thread.sleep(300);
			System.out.println(
					"          #  ##  ## ##   ## ##   ##            ##  ##   ### ## ### ##  ## ##   ##           ##  #  ###   ## ##  ##   ##      ###   ### ");
			Thread.sleep(250);
			System.out.println(
					"          ####    ##### ### ###  ####          ##  ##    ####   ####  ### ###  ####        ###### ## ##  ####  ####   #### ####  ####  ");
			Thread.sleep(200);
			System.out.println(
					"                                #  ###                                        #  ###                     ##                            ");
			Thread.sleep(150);
			System.out.println(
					"                                #####                                         #####                     ####                           ");
			
			Thread.sleep(1000);
		} catch (Exception e) {
		}

	}
}
