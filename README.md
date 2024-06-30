# WEB APPLICATIONS (WebApp) - REPOSITORY TEMPLATE #

This repository is a template repository for the homeworks to be developed in the [Web Applications](https://stem.elearning.unipd.it/course/view.php?id=8494) course.

*Web Applications* is a course of the

* [Master Degree in Computer Engineering](https://degrees.dei.unipd.it/master-degrees/computer-engineering/) of the  [Department of Information Engineering](https://www.dei.unipd.it/en/), [University of Padua](https://www.unipd.it/en/), Italy.
* [Master Degree in Data Science](https://datascience.math.unipd.it/) of the  [Department of Mathematics "Tullio Levi-Civita"](https://www.math.unipd.it/en/), [University of Padua](https://www.unipd.it/en/), Italy.

## Group members ##
| NAME        | SURNAME     | EMAIL                                            |
|-------------|-------------|--------------------------------------------------|
| Ahmad       | Sadin       |ahmad.sadin@studenti.unipd.it                     |
| Elnaz       | Dolati      |elnaz.dolati@studenti.unipd.it                    |
| Francesco   | Chemello    |francesco.chemello.1@studenti.unipd.it            |
| Gabriella   | Farias      |gabriellaingridy.desouzafarias@studenti.unipd.it  |
| Luca        | Pellegrini  |luca.pellegrini.5@studenti.unipd.it               |
| Seyedreza   | Safavi      |seyedrezasafavi@studenti.unipd.it                 |

### Organisation of the repository ###
The repository is organised as follows:

* <u>code:</u> folder that contains all the code for homework 1 and homework 2 + files for git and maven.
    * <u>.mvn:</u> folder for maven.
    * <u>src/main:</u> main folder of the code.
        * <u>database:</u> folder that contains all the schemas for the database.
        * <u>java:</u> folder that contains all the java code for the application layer of the web application.
            * <u>dao:</u> contains all the DAO methods to access to the database.
            * <u>rest:</u> contains all the REST methods.
            * <u>service:</u> contains the abstract classes of all the entity-relation of the database.
            * <u>servlet:</u> contains all the servlet functions to manipulate data in the database.
        * <u>resources:</u> contains the log xml files.
        * <u>webapp:</u> contains the presentation layer of the web application.
            * <u>css:</u> contains all the css files.
            * <u>html:</u> contains all the html files.
            * <u>img:</u> contains all the images for the site.
            * <u>js:</u> contains all the javascript files.
            * <u>jsp:</u> contains all the jsb files.
            * <u>META-INF:</u> contains the configuration file for the database.
            * <u>WEB-INF:</u> contains the configuration file of the web application.
* <u>homework-1:</u> this folder contains the report of the application layer of the web application.
* <u>homework-2:</u> this folder contains the final presentation of the course.
* <u>developing:</u> contains the files that the group uses for developing the application.

#### License ####

All the contents of this repository are shared using the [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/).

![CC logo](https://i.creativecommons.org/l/by-sa/4.0/88x31.png)