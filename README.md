# mortgage-calculation-back-end
Backend API for the mortgage-calculation-front-end

NB: this project is used with the mortgaga-calculation-front-end: please download and set it up: https://github.com/jarred1111/mortgage-calculator-font-end.git

HOW TO RUN:
Download the zip file.

SETTING UP THE DB:
Open MySql workbench.
Click -> Server tab -> Data Import. Select the location of the DB folder (Folder name: mortage-cal-DB). Click Start Import.

STARTING THE PROJECT:
Open eclipse, one folder up from the project folder.
Right click in the Project Viewer -> select the import dropdown -> select the import button at the bottom.
A window should open. Select Maven -> Existing Maven Projects -> click next.
Browse the directories and select the project folder. If the project isn't selected then select it and click finish.

Once open: 
GO to : /src/main/java/com/mortageCal/MortageCalculation/dao/CalculationDao.java and Change {DB-password} to your password used with MySql and change your username if it isn't root.

Right click on the project and run a maven build. (mvn clean install)
Once complete, go to: /src/main/java/com/mortageCal/MortageCalculation/MortageCalculationApplication.java -> right click and select -> run as -> java application.
