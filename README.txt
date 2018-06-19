Design of the code:
the code use Conference.java as the entry, use ManageConference.java to control the flow of generating the schedule. There are 3 data models designed for this project, and 3 services implememnted to enable input output and core logic. There's a config file inside service package to provide constants.

*************************************************************************************************************

Consideration:
Now we have a fairly small set up, however the codebase might grow rapidly. Therefore we seperate the logic in different services, which can be later on evole into seprate modules or even micro-services. 
Note that we skipped the adapter layer in this proect, since it's just too small, however it's important to point out.

*************************************************************************************************************
Logic:
the logic of the code is pretty simple:

1. extract all talks from file, and sort them in descending order by talk duration.
2. calculate how many tracks might be used, and init the tracks and sessions.
3. allocate talks to sessions, we find the longest talk to fill in the most empty session. 
4. allocate sessions to tracks, according to morning and afternoon, allocate constructed sessions to tracks.
5. done

*************************************************************************************************************

how to run the code:

1. if you are using IDEs like intellij, just import the project and run right click Conference.java, select "Run Conference.main()"
2. if you prefer CLI to run the project: try: {mvn exec:java -Dexec.mainClass="Conference"} in the route directory(thoughworks).
3. you can specify the input file in thoughworks/resources, if your file name is not "input", please change the file name in ServiceConfig accordingly

*************************************************************************************************************
