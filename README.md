a. Any special Instructions to run the tests (if needed)

All of the test can be run by 'mvn clean test' in terminal. However, for question 9 regarding MBean only MemAppender is monitored.
Therefore when marking question 9 it may be easier to run the MemAppenderStressTest individually so you can open JConsole to check.


b.A reflection on the test coverage reports (in particular, an explanation why certain parts of the code are not covered).
Insert a summary of the reports (e.g., embed a screenshot)

My tests have 100% coverage for MemAppender, 94% for T2Layout and 90% for T2Layout
<img src ="jacoco-coverage.png"

c.An evaluation which of the layouts, T1Layout or T2Layout, you recommend for use. Base your decision on your experience (ease of use),
technical aspects (e.g. performance as shown in the stress tests, stability, number and size of direct and indirect dependencies), 
and social aspects (size and activity of developer community, license, support like mailing lists and stackoverflow topics, usage by others, …) 



The README should be brief, at least 300 but not more than 800 words
