# CS-4341-AI
1. Connect-N


## Setting Up With Eclipse
Have the following: 

1. Eclipse Luna 
1. The following Plugins:
   1. Groovy-Eclipse Feature
   1. Gradle IDE
   1. Automatic building is **disabled**


By command line, go to the root of the eclipse project. If you don't have gradle installed:

* Call `gradlew.bat eclipse`. This will create eclipse project files, etc.

Else:

* Call `gradle eclipse`. This will create eclipse project files, etc.

More steps!

1. Import the repository and project into eclipse like normal.
1. Right click on the project, select _Properties_
1. Go to **Builders** and click "New"
    1. Name it Gradle-Windows (if applicable)
    1. Location: `${workspace_loc:/ConnectFourAI/gradlew.bat}`
    1. Working Directory: `${workspace_loc:/ConnectFourAI}`
    1. Arguments: `allJars`
1. It should be working now...



## Running AI's In Eclipse
Use the run configurations. Its super easy. Commandline also works if yout want to start batching.

### Making New Run Configurations
1. Select the ConnectFourAI project
1. From the tool bar, select Run->Run Configurations
1. Scroll down to **Java Application** and click the *New... * button
1. Rename the configuration
1. Pick the **ConnectFourAI** project
1. Pick the **referee.Referee** Main class
1. "java -jar JAR1 args" "java -jar JAR2 args" 6 7 3 10 10
    1. JAR1 and JAR2 should be absolute file paths inside the project
    1. The numbers at the end represent configurations for the game. See the project instructions
    1. _args_ is optional. See [Aguments for AI Players](#player_args)


### <a name='player_args' />Aguments for AI Players
These arguments can be used for player AI.
| Arg Number  | Name  | Description  |
|---|---|---|
| 0  | Player Name  | The name of a player (so that player jars can be duplicated)  |
| 1  |   |   |  
| 2  |   |   | 


### Jar File Locations
Inside the ConnectFourAI project, the given jars are in the **libs** folder. Generated player jars are located in **build\libs**.


## Making New Players
1. Make a new Player class that extends AbstractPlayer in the src/main/java/player package
1. Fill out the overriden methods
1. In build.gradle above the _task allJars_ declaration, add the following with CLASS_NAME replaced with the name of your class.
``` groovy
/** Task that makes the CLASS_NAME jar */
task CLASS_NAMEJar(type: Jar) {
  String class_name = "CLASS_NAME"
  from(sourceSets.main.output) {
    include "player/**"
  }
  
  manifest.attributes['Class-Path'] = "."
  manifest.attributes['Main-Class'] = "player." + class_name
  archiveName = class_name + ".jar"
}
```


## FAQ
### Why won't some of these errors go away?
1. Clean and rebuild
1. Refresh the project
1. Close and re-open the project (Eclipse + gradle isn't perfect)

### Why does eclipse want to change the java compliance to 1.5 blah blah blah?
idk



