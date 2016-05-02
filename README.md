# Installation 

1) Unzip project to local disk
1) Install JDK 8 or above
2) Install apache-maven 3 or above

# Run application
  
  Run terminal and cd to unzipped project folder
  Run sample  
  
           mvn clean compile exec:java -Dexec.mainClass="AnagramsTest"
    
  Run test with random generated file:
        
        mvn clean compile exec:java -Dexec.mainClass="AnagramsTest" -Dexec.args="src/main/resources/test.txt 1000000"   
   
   
   



