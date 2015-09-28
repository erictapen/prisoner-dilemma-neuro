![This is the fitness function over evolution steps, plotted with jfreecharts](https://github.com/erictapen/prisoner-dilemma-neuro/blob/master/fitness_chart.png?raw=true)
# Building from sources 
## GNU/Linux and OS X 
Maven and Git are required. You propably know where to get it. If not, you might have luck with typing `sudo apt-get install git mvn` into a terminal. 
Now enter the following commands into your terminal: 
``` 
git clone https://github.com/erictapen/prisoner-dilemma-neuro.git 
cd prisoner-dilemma-neuro
mvn package 
``` 
If the build was successful, the resulting Jar file is located at `target/prisoner-dilemma-neuro-VERSION.jar` 
To run the program, enter
``` 
java -jar target/prisoner-dilemma-neuro-VERSION.jar
``` 
In order to change settings, modify the file `src/main/java/Settings.java` and rebuild.
## Windows 
Unfortunately, I don't have a step-by-step solution here. You might get it running using Maven. If you got a working solution, please create a Pull Request! 


